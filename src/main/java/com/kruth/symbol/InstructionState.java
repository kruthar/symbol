package com.kruth.symbol;

import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.instructions.*;
import com.kruth.symbol.lexers.LineLexer;
import com.kruth.symbol.literals.Literal;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by kruthar on 3/15/16.
 */
public class InstructionState {
    private boolean inComment = false;
    private LineLexer lineLexer = null;

    private Stack<Integer> loopStack = new Stack<>();
    private Map<String, Literal> variableMap = new HashMap<>();
    private Map<String, Function> functionMap = new HashMap<>();

    protected InstructionState() {}

    public void setVariable(String name, Literal value) {
        variableMap.put(name, value);
    }

    public Literal getVariable(String name) {
        return variableMap.get(name);
    }

    public boolean hasVariable(String name) {
        return variableMap.containsKey(name) && variableMap.get(name) != null;
    }

    public void setLineLexerFile(String filename) {
        lineLexer = new LineLexer(new File(getClass().getClassLoader().getResource(filename).getFile()));
    }

    public void setLineLexerString(String line) {
        lineLexer = new LineLexer(line);
    }

    public Boolean hasNextLine() {
        return lineLexer.hasNext();
    }

    public String nextLine() {
        return lineLexer.next();
    }

    public String peekNextLine() {
        return lineLexer.peek();
    }

    public void pushCurrentLoopMarker() {
        loopStack.push(lineLexer.getIndex());
    }

    public void resetToCurrentLoopMarker() {
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

    public void routeNextInstruction(Boolean execute) {
        String instruction = nextLine();

        // Return if we have an empty line
        if (instruction.trim().length() < 1) {
            return;
        }

        String[] instructionSplit = instruction.trim().split(" ", 2);

        switch (instructionSplit[0].toLowerCase()) {
            case "blockcomment":
                BlockComment.parse(this, instruction, execute);
                break;
            case "comment":
                Comment.parse(instruction, execute);
                break;
            case "print":
                Print.parse(this, instructionSplit[1], execute);
                break;
            case "println":
                Println.parse(this, instructionSplit[1], execute);
                break;
            case "if":
                If.parse(this, instructionSplit[1], execute);
                break;
            case "while":
                While.parse(this, instructionSplit[1], execute);
                break;
            case "function":
                Function.parse(this, instructionSplit[1]);
            default:
                String[] variableSplit = instructionSplit[1].split(" ", 2);
                if (variableSplit[0].equals("is")) {
                    if (execute) {
                        setVariable(instructionSplit[0], new Expression(this, variableSplit[1]).evaluate());
                    }
                } else {
                    System.out.println("Unknown instruction '" + instructionSplit[0] + "'");
                }
        }
    }
}
