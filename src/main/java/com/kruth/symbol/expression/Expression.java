package com.kruth.symbol.expression;

import com.kruth.symbol.ErrorState;
import com.kruth.symbol.LanguageObject;
import com.kruth.symbol.dots.Dot;
import com.kruth.symbol.dots.DotParser;
import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.comparators.*;
import com.kruth.symbol.exceptions.*;
import com.kruth.symbol.instructions.BlockComment;
import com.kruth.symbol.instructions.Module;
import com.kruth.symbol.instructions.Variable;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.*;
import com.kruth.symbol.operations.*;
import com.kruth.symbol.structures.SymbolList;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.UnexpectedException;
import java.util.*;

/**
 * Created by kruthar on 2/24/16.
 */
public class Expression implements ExpressionComponent {
    private static final Map<String, Integer> KEYWORDS;
    static {
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put("open", 0);
        aMap.put("close", 1);
        aMap.put("sep", 2);
        aMap.put("blockcomment", 3);
        aMap.put("comment", 4);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    private InstructionState instructionState;
    private List<ExpressionComponent> components = new ArrayList<>();

    // This is a signal that we opened this expression with the 'opened' keyword
    private Boolean opened = false;

    public Expression(InstructionState instructionState, String expressionString) throws SymbolException {
        this(instructionState, new SpaceLexer(expressionString));
    }

    public Expression(InstructionState instructionState, SpaceLexer lexer, Boolean opened) throws SymbolException{
        this.opened = opened;
        parse(instructionState, lexer);
    }

    public Expression(InstructionState instructionState, SpaceLexer lexer) throws SymbolException {
        parse(instructionState, lexer);
    }

    public Expression(SymbolObject obj) {
        components.add(obj);
    }

    public void parse(InstructionState instructionState, SpaceLexer lexer) throws SymbolException {
        this.instructionState = instructionState;
        components = new ArrayList<>();

        while (lexer.hasNext()) {
            if (Expression.hasKeyword(lexer.peek().toLowerCase())) {
                String keyword = lexer.peek();
                // If we are opening an expression then close the current expression,
                // then pass the lexer to in inner expression
                if (keyword.equals("open")) {
                    lexer.next();
                    addComponent(new Expression(instructionState, lexer, true));
                } else if (keyword.equals("close")) {
                    if (opened) {
                        lexer.next();
                    }
                    // If we are closing an expression, then end the loop here and allow the Expression to finish
                    // and the lexer to continue in the upper level of the stack
                    break;
                } else if (keyword.equals("sep")) {
                    lexer.next();
                    break;
                } else if (keyword.equals("blockcomment")) {
                    lexer.next();
                    BlockComment.midParse(instructionState, lexer);
                } else if (keyword.equals("comment")) {
                    lexer.next();
                    break;
                } else {
                    lexer.next();
                    throw new UnexpectedKeywordException("Unrecognized Expression keyword: " + keyword);
                }
            } else if (SymbolNull.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(new SymbolNull(lexer));
            } else if (SymbolNumber.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(new SymbolNumber(lexer));
            } else if (SymbolString.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(new SymbolString(lexer));
            } else if (SymbolBoolean.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(new SymbolBoolean(lexer));
            } else if (SymbolList.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(new SymbolList(instructionState, lexer));
            } else if (OperationParser.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(OperationParser.parse(lexer));
            } else if (ComparatorParser.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(ComparatorParser.parse(lexer));
            } else if (DotParser.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(DotParser.parse(instructionState, lexer));
            } else if (instructionState.hasModule(lexer.peek().toLowerCase()) &&
                    instructionState.hasModule(lexer.peek().toLowerCase())) {
                addComponent(instructionState.getModule(lexer.next().toLowerCase()));
            } else if (instructionState.hasFunction(lexer.peek().toLowerCase())) {
                addComponent(instructionState.parseFunctionCall(instructionState, lexer));
            } else {
                if (instructionState.hasVariable(lexer.peek().toLowerCase())) {
                    addComponent(new Variable(lexer.next()));
                } else {
                    throw new VariableDoesNotExistsException("Variable " + lexer.peek().toLowerCase() + " does not exist.");
                }
            }
        }
    }

    public void addComponent(ExpressionComponent component) {
        // This logic not actually necessary for happy path so commenting out to allow method components.
        // Will probably have to reinstitue this plus more logic around when to allow adding method components at some point.
//        if (!components.isEmpty() && (
//            (components.get(components.size() - 1) instanceof Literal && component instanceof Literal) ||
//            (components.get(components.size() -1) instanceof Operation && component instanceof Operation))) {
//            System.out.println("bad expression - two literals or two operations");
//            System.exit(1);
//        }
        components.add(component);
    }

    public String toString() {
        String result = "(";

        for (ExpressionComponent component: components) {
            result += component.toString();
        }

        result += ")";

        return result;
    }

    public SymbolObject evaluate() throws SymbolException {
        List<ExpressionComponent> reducedComponents = new ArrayList<>();
        // Run through the component list and evaluate any sub expressions, and retrieve variable values first
        for (ExpressionComponent component: components) {
            if (component instanceof Expression) {
                reducedComponents.add(((Expression) component).evaluate());
            } else if (component instanceof Variable) {
                reducedComponents.add(instructionState.getVariable(((Variable) component).getName()));
            } else {
                reducedComponents.add(component);
            }
        }

        // Apply dot methods
        while (reducedComponents.size() > 1) {
            boolean foundOperation = false;
            int i = 1;

            while (i < reducedComponents.size()) {
                // Essentially noop for now
                if (reducedComponents.get(i) instanceof Dot) {
                    SymbolObject newObject;
                    if (reducedComponents.get(i - 1) instanceof Module) {
                        // If you can figure out how to get the module methods and variables into the Module object itself, then we could just do the typical invokation.
                        // Possible ways to do this:
                        // 1. Some sor tof code generation, 'add' methods to an existing object (I think I read this is not possible)
                        // 2. Intercept invokations and reroute to dynamic methods (I think this is possible but not simple)
                        // 3. Make Modules just POJO's. (But that's no fun, let's eat our own dog food and make Modules coded in Symbol)
                        newObject = ((Module) reducedComponents.get(i - 1)).invoke((Dot) reducedComponents.get(i));
                    } else {
                        newObject = invokeMethod((SymbolObject) reducedComponents.get(i - 1), (Dot) reducedComponents.get(i));
                    }
                    reducedComponents.remove(i - 1);
                    reducedComponents.remove(i - 1);
                    reducedComponents.add(i - 1, newObject);
                } else {
                    i++;
                }
            }

            if (!foundOperation) {
                break;
            }
        }

        // After all sub expressions have been reduced, perform typical order of operations
        // Times & DividedBy
        while (reducedComponents.size() > 1) {
            boolean foundOperation = false;

            for (int i = 1; i < reducedComponents.size(); i += 2) {
                Literal newLiteral = null;
                if (reducedComponents.get(i) instanceof Times) {
                    try {
                        newLiteral = ((Literal) reducedComponents.get(i - 1)).times((Literal) reducedComponents.get(i + 1));
                    } catch (OperationNotSupportedException e) {
                        System.out.println("Times operation not supported with these types.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                } else if (reducedComponents.get(i) instanceof DividedBy) {
                    try {
                        newLiteral = ((Literal) reducedComponents.get(i - 1)).dividedby((Literal) reducedComponents.get(i + 1));
                    } catch (OperationNotSupportedException e) {
                        System.out.println("DividedBy operation not supported with these types.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                } else if (reducedComponents.get(i) instanceof Modulo) {
                    try {
                        newLiteral = ((Literal) reducedComponents.get(i - 1)).modulo((Literal) reducedComponents.get(i + 1));
                    } catch (OperationNotSupportedException e) {
                        System.out.println("Modulo operation not supported with these types.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                }

                if (newLiteral != null) {
                    reduceComponents(reducedComponents, i, newLiteral);
                    foundOperation = true;
                }
            }

            if (!foundOperation) {
                break;
            }
        }

        // Plus & Minus
        while (reducedComponents.size() > 1) {
            boolean foundOperation = false;

            for (int i = 1; i < reducedComponents.size(); i += 2) {
                Literal newLiteral = null;
                if (reducedComponents.get(i) instanceof Plus) {
                    newLiteral = ((Literal) reducedComponents.get(i - 1)).plus((Literal) reducedComponents.get(i + 1));
                } else if (reducedComponents.get(i) instanceof Minus) {
                    try {
                        newLiteral = ((Literal) reducedComponents.get(i - 1)).minus((Literal) reducedComponents.get(i + 1));
                    } catch (OperationNotSupportedException e) {
                        System.out.println("Minus operation not supported with these types.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                }

                if (newLiteral != null) {
                    reduceComponents(reducedComponents, i, newLiteral);
                    foundOperation = true;
                }
            }

            if (!foundOperation) {
                break;
            }
        }

        // Reduce Comparative expressions
        while (reducedComponents.size() > 1) {
            boolean foundOperation = false;

            for (int i = 1; i < reducedComponents.size(); i += 2) {
                Literal newLiteral = null;
                if (reducedComponents.get(i) instanceof Equals) {
                    newLiteral = new SymbolBoolean(reducedComponents.get(i - 1).equals(reducedComponents.get(i + 1)));
                } else if (reducedComponents.get(i) instanceof NotEquals) {
                    newLiteral = new SymbolBoolean(!reducedComponents.get(i - 1).equals(reducedComponents.get(i + 1)));
                } else if (reducedComponents.get(i) instanceof GreaterThan) {
                    newLiteral = ((Literal) reducedComponents.get(i - 1)).greaterThan((Literal) reducedComponents.get(i + 1));
                } else if (reducedComponents.get(i) instanceof LessThan) {
                    newLiteral = ((Literal) reducedComponents.get(i - 1)).lessThan((Literal) reducedComponents.get(i + 1));
                } else if (reducedComponents.get(i) instanceof GreaterThanEquals) {
                    newLiteral = ((Literal) reducedComponents.get(i - 1)).greaterThanOrEqualTo((Literal) reducedComponents.get(i + 1));
                } else if (reducedComponents.get(i) instanceof LessThanEquals) {
                    newLiteral = ((Literal) reducedComponents.get(i - 1)).lessThanOrEqualTo((Literal) reducedComponents.get(i + 1));
                }

                if (newLiteral != null) {
                    reduceComponents(reducedComponents, i, newLiteral);
                    foundOperation = true;
                }
            }

            if (!foundOperation) {
                break;
            }
        }

        // Combine Boolean statments with Or and And operators
        while (reducedComponents.size() > 1) {
            boolean foundOperation = false;

            for (int i = 1; i < reducedComponents.size(); i += 2) {
                SymbolBoolean newBool = null;
                if (reducedComponents.get(i) instanceof Or &&
                        reducedComponents.get(i - 1) instanceof SymbolBoolean &&
                        reducedComponents.get(i + 1) instanceof SymbolBoolean) {
                    newBool = (SymbolBoolean) ((SymbolBoolean) reducedComponents.get(i - 1)).plus((SymbolBoolean) reducedComponents.get(i + 1));
                }

                if (reducedComponents.get(i) instanceof And &&
                        reducedComponents.get(i - 1) instanceof SymbolBoolean &&
                        reducedComponents.get(i + 1) instanceof SymbolBoolean) {
                    newBool = (SymbolBoolean) ((SymbolBoolean) reducedComponents.get(i - 1)).times((SymbolBoolean) reducedComponents.get(i + 1));
                }

                if (newBool != null) {
                    reduceComponents(reducedComponents, i, newBool);
                    foundOperation = true;
                }
            }

            if (!foundOperation) {
                break;
            }
        }

        if (reducedComponents.size() > 1) {
            throw new InvalidExpressionException("Expression failed to completely reduce expression, remaining expression is: " + reducedComponents);
        }

        return (SymbolObject) reducedComponents.get(0);
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }

    private void reduceComponents(List list, int index, Literal newLiteral) {
        list.remove(index - 1);
        list.remove(index - 1);
        list.remove(index - 1);
        list.add(index - 1, newLiteral);
    }

    private SymbolObject invokeMethod(SymbolObject parent, Dot dot) throws SymbolException {
        LanguageObject obj = parent.getLanguageObject();
        for (Method method: obj.getClass().getDeclaredMethods()) {
            if (method.getName().equals(dot.getName())) {
                Class<?>[] types = method.getParameterTypes();
                List<SymbolObject> parameters = dot.getParameters();

                if (types.length != parameters.size()) {
                    continue;
                }

                for (int i = 0; i < types.length; i++) {
                    if (!types[i].isAssignableFrom(parameters.get(i).getClass())) {
                        continue;
                    }
                }

                try {
                    return (SymbolObject) method.invoke(obj, parameters.toArray());
                } catch (IllegalAccessException e) {
                    throw new CatastrophicFailureException("IllegalAccessException thrown trying to invoke " + dot.getName() + " with parameters: " + dot.getParameters());
                } catch (InvocationTargetException e) {
                    throw (SymbolException) e.getCause();
                }
            }
        }

        throw new DotMethodNotDefinedException("Failed to invoke " + dot + " on <" + obj.getClass() + "> " + obj);
    }
}
