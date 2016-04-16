package com.kruth.symbol;

import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.instructions.Variable;
import com.kruth.symbol.instructions.*;
import com.kruth.symbol.lexers.LineLexer;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.SymbolNull;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * Created by kruthar on 3/15/16.
 */
public class InstructionState {
    private LineLexer lineLexer = null;
    private InstructionState parentState = null;
    private SymbolObject returnValue = null;
    private Boolean continueBlockComment = false;

    private Stack<Integer> loopStack = new Stack<>();
    private Map<String, SymbolObject> variableMap = new HashMap<>();
    private Map<String, Function> functionMap = new HashMap<>();

    public InstructionState() {}

    public void setVariable(String name, SymbolObject value) {
        if (parentState != null && parentState.hasVariable(name)) {
            parentState.setVariable(name, value);
        } else {
            variableMap.put(name, value);
        }
    }

    public SymbolObject getVariable(String name) {
        if (variableMap.containsKey(name)) {
            return variableMap.get(name);
        } else {
            return parentState.getVariable(name);
        }
    }

    public boolean hasVariable(String name) {
        Boolean hasLocal = variableMap.containsKey(name) && variableMap.get(name) != null;
        Boolean hasParent = false;

        if (parentState != null) {
            hasParent = parentState.hasVariable(name);
        }

        return hasLocal || hasParent;
    }

    public void setLineLexerFile(String filename) {
        File symbFile;
        URL resourceURL = getClass().getClassLoader().getResource(filename);

        // If file isn't in the resources folder then look at the absolute path.
        if (resourceURL != null) {
            symbFile = new File(resourceURL.getFile());
        } else {
            symbFile = new File(filename);
        }
        lineLexer = new LineLexer(symbFile);
    }

    public void setLineLexerString(String line) {
        lineLexer = new LineLexer(line);
    }

    public void setLineLexerList(List<String> instructions) {
        lineLexer = new LineLexer(instructions);
    }

    public void setParentState(InstructionState parent) {
        parentState = parent;
    }

    public Boolean hasNextLine() {
        return lineLexer.hasNext();
    }

    public String nextLine() {
        ErrorState.incrementLine();
        return lineLexer.next();
    }

    public String peekNextLine() {
        return lineLexer.peek();
    }

    public void pushCurrentLoopMarker() {
        loopStack.push(lineLexer.getIndex());
    }

    public void resetToCurrentLoopMarker() {
        ErrorState.decrementLine(lineLexer.getIndex() - loopStack.peek());
        lineLexer.setIndex(loopStack.peek());
    }

    public void popCurrentLoopMarker() {
        if (!loopStack.empty()) {
            loopStack.pop();
        }
    }

    public void addFunction(Function func) {
        if (functionMap.containsKey(func.getKey())) {
            System.out.println("This function signature already exists, should this be an error?");
            System.exit(1);
        }

        functionMap.put(func.getKey(), func);
    }

    public Boolean hasFunction(String name) {
        Boolean hasLocal = functionMap.containsKey(name);
        Boolean hasParent = false;

        if (parentState != null) {
            hasParent = parentState.hasFunction(name);
        }

        return hasLocal || hasParent;
    }

    public SymbolObject parseFunctionCall(InstructionState instructionState, SpaceLexer lexer) throws VariableDoesNotExistsException {
        String name = lexer.peek();
        if (functionMap.containsKey(name)) {
            lexer.next();
            Function function = functionMap.get(name);

            Map<String, Expression> parameterExpressionMap = new HashMap<>();

            for(String parameter: function.getParameters()) {
                parameterExpressionMap.put(parameter, new Expression(instructionState, lexer));
            }

            return function.execute(instructionState, parameterExpressionMap);
        } else if (parentState != null && parentState.hasFunction(name)) {
            return parentState.parseFunctionCall(parentState, lexer);
        } else {
            System.out.println("ERROR: Function is not defined: " + name);
            System.exit(1);
        }

        return new SymbolNull();

    }

    public void routeNextInstruction(Boolean execute) throws VariableDoesNotExistsException {
        if (continueBlockComment) {
            continueBlockComment = false;
            BlockComment.parse(this, execute);
        }

        String instruction = nextLine();
        routeInstruction(instruction, execute);
    }

    public void routeLexerInstruction(SpaceLexer lexer, Boolean execute) throws VariableDoesNotExistsException {
        routeInstruction(lexer.restOfLine(), execute);
    }

    public void routeInstruction(String instruction, Boolean execute) throws VariableDoesNotExistsException {
        // Return if we have an empty line
        if (instruction.trim().length() < 1) {
            return;
        }

        String[] instructionSplit = instruction.trim().split(" ", 2);

        switch (instructionSplit[0].toLowerCase()) {
            case "blockcomment":
                BlockComment.parse(this, execute);
                break;
            case "comment":
                Comment.parse(instruction, execute);
                break;
            case "print":
                Print.parse(this, instruction, execute);
                break;
            case "println":
                Println.parse(this, instruction, execute);
                break;
            case "if":
                If.parse(this, instructionSplit[1], execute);
                break;
            case "while":
                While.parse(this, instructionSplit[1], execute);
                break;
            case "function":
                Function.parse(this, instructionSplit[1]);
                break;
            case "execute":
                Expression execExpression = new Expression(this, instructionSplit[1]);
                execExpression.evaluate();
                break;
            case "return":
                Expression returnExpression = new Expression(this, instructionSplit[1]);
                if (execute) {
                    this.setReturnValue(returnExpression.evaluate());
                    this.endInstructions();
                }
                break;
            case "variable":
                Variable.parse(this, instructionSplit[1], execute);
                break;
            case "for":
                For.parse(this, instructionSplit[1], execute);
                break;
            case "foreach":
                Foreach.parse(this, instructionSplit[1], execute);
                break;
            default:
                System.out.println("Unknown instruction '" + instructionSplit[0] + "'");
        }

        // If we have a return value then ensure we end here
        if (returnValue != null) {
            endInstructions();
        }
    }

    public void setReturnValue(SymbolObject val) {
        returnValue = val;
    }

    public SymbolObject getReturnValue() {
        return returnValue;
    }

    public void endInstructions() {
        while (this.hasNextLine()) {
            this.nextLine();
        }
    }

    public void setContinueBlockComment(Boolean set) {
        continueBlockComment = set;
    }

    public List<String> advanceTo(String stop) {
        List<String> lines = new ArrayList<>();
        while (hasNextLine()) {
            String next = nextLine();
            String[] nextSplit = next.trim().split(" ", 2);

            if (nextSplit[0].equals(stop)) {
                break;
            }

            lines.add(next);
        }

        ErrorState.decrementLine(lines.size() + 1);
        return lines;
    }

    public List<String> advanceToLoopScoped() {
        List<String> starts = Arrays.asList("while", "foreach");
        String stop = "done";
        List<String> lines = new ArrayList<>();
        while (hasNextLine()) {
            String next = nextLine();
            String[] nextSplit = next.trim().split(" ", 2);
            int scope = 0;

            if (starts.contains(nextSplit[0])) {
                scope++;
            } if (nextSplit[0].equals(stop)) {
                if (scope == 0) {
                    break;
                } else {
                    scope--;
                }
            }

            lines.add(next);
        }

        ErrorState.decrementLine(lines.size() + 1);
        return lines;
    }

    public Map<String, Function> getFunctionMap() {
        return functionMap;
    }

    public void setFunctionMap(Map<String, Function> map) {
        functionMap = new HashMap<>(map);
    }
}
