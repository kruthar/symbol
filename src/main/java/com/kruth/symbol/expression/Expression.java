package com.kruth.symbol.expression;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.comparators.*;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.Literal;
import com.kruth.symbol.literals.SymbolBoolean;
import com.kruth.symbol.literals.SymbolNumber;
import com.kruth.symbol.literals.SymbolString;
import com.kruth.symbol.operations.*;

import javax.naming.OperationNotSupportedException;
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
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    private List<ExpressionComponent> components = new ArrayList<>();
    private InstructionState instructionState = InstructionState.getInstance();

    public Expression() {}

    public Expression(ExpressionComponent component) {
        components = Arrays.asList(component);
    }


    public Expression(String expressionString) {
        this(new SpaceLexer(expressionString));
    }

    public Expression(SpaceLexer lexer) {
        components = new ArrayList<>();

        while (lexer.hasNext()) {
            if (Expression.hasKeyword(lexer.peek().toLowerCase())) {
                String keyword = lexer.next();
                // If we are opening an expression then close the current expression,
                // then pass the lexer to in inner expression
                if (keyword.equals("open")) {
                    addComponent(new Expression(lexer));
                } else if (keyword.equals("close")) {
                    // If we are closing an expression, then end the loop here and allow the Expression to finish
                    // and the lexer to continue in the upper level of the stack
                    break;
                } else {
                    System.out.println("Unrecognized Expression keyword: " + keyword);
                    System.exit(1);
                }
            } else if (SymbolNumber.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(new SymbolNumber(lexer));
            } else if (SymbolString.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(new SymbolString(lexer));
            } else if (SymbolBoolean.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(new SymbolBoolean(lexer));
            } else if (OperationParser.hasKeyword(lexer.peek().toLowerCase())) {
                addComponent(OperationParser.parse(lexer));
            } else if (ComparatorParser.hasKeyword(lexer.peek().toLowerCase())){
                addComponent(ComparatorParser.parse(lexer));
            } else if (instructionState.hasVariable(lexer.peek().toLowerCase())) {
                addComponent(new Variable(lexer.next()));
            } else {
                System.out.println("Unrecognized Expression start: " + lexer.peek().toLowerCase());
                System.exit(1);
            }
        }
    }

    public void addComponent(ExpressionComponent component) {
        if (!components.isEmpty() && (
            (components.get(components.size() - 1) instanceof Literal && component instanceof Literal) ||
            (components.get(components.size() -1) instanceof Operation && component instanceof Operation))) {
            System.out.println("bad expression - two literals or two operations");
            System.exit(1);
        }
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

    public Literal evaluate() {
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
                    try {
                        newLiteral = ((Literal) reducedComponents.get(i - 1)).equalTo((Literal) reducedComponents.get(i + 1));
                    } catch (OperationNotSupportedException e) {
                        System.out.println("EqualTo operation not supported with these types.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                } else if (reducedComponents.get(i) instanceof NotEquals) {
                    try {
                        newLiteral = ((Literal) reducedComponents.get(i - 1)).notEqualTo((Literal) reducedComponents.get(i + 1));
                    } catch (OperationNotSupportedException e) {
                        System.out.println("NotEqualTo operation not supported with these types.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                } else if (reducedComponents.get(i) instanceof GreaterThan) {
                    try {
                        newLiteral = ((Literal) reducedComponents.get(i - 1)).greaterThan((Literal) reducedComponents.get(i + 1));
                    } catch (OperationNotSupportedException e) {
                        System.out.println("GreaterThan operation not supported with these types.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                } else if (reducedComponents.get(i) instanceof LessThan) {
                    try {
                        newLiteral = ((Literal) reducedComponents.get(i - 1)).lessThan((Literal) reducedComponents.get(i + 1));
                    } catch (OperationNotSupportedException e) {
                        System.out.println("LessThan operation not supported with these types.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                } else if (reducedComponents.get(i) instanceof GreaterThanEquals) {
                    try {
                        newLiteral = ((Literal) reducedComponents.get(i - 1)).greaterThanOrEqualTo((Literal) reducedComponents.get(i + 1));
                    } catch (OperationNotSupportedException e) {
                        System.out.println("GreaterThanEquals operation not supported with these types.");
                        e.printStackTrace();
                        System.exit(1);
                    }
                } else if (reducedComponents.get(i) instanceof LessThanEquals) {
                    try {
                        newLiteral = ((Literal) reducedComponents.get(i - 1)).lessThanOrEqualTo((Literal) reducedComponents.get(i + 1));
                    } catch (OperationNotSupportedException e) {
                        System.out.println("LessThanEquals operation not supported with these types.");
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

        // Combine Boolean statments with Or and And operators
        while (reducedComponents.size() > 1) {
            boolean foundOperation = false;

            for (int i = 1; i < reducedComponents.size(); i += 2) {
                SymbolBoolean newBool = null;
                if (reducedComponents.get(i) instanceof Or &&
                        reducedComponents.get(i - 1) instanceof SymbolBoolean &&
                        reducedComponents.get(i + 1) instanceof SymbolBoolean) {
                    newBool = new SymbolBoolean(((SymbolBoolean) reducedComponents.get(i - 1)).getValue() || ((SymbolBoolean) reducedComponents.get(i + 1)).getValue());
                }

                if (reducedComponents.get(i) instanceof And &&
                        reducedComponents.get(i - 1) instanceof SymbolBoolean &&
                        reducedComponents.get(i + 1) instanceof SymbolBoolean) {
                    newBool = new SymbolBoolean(((SymbolBoolean) reducedComponents.get(i - 1)).getValue() && ((SymbolBoolean) reducedComponents.get(i + 1)).getValue());
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
            System.out.println("ERROR: Did not fully reduce operation");
            System.out.println();
        }
        return (Literal) reducedComponents.get(0);
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
}
