package com.kruth.symbol;

import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.literals.Literal;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kruthar on 3/15/16.
 */
public class InstructionState {
    private boolean inComment = false;
    private static InstructionState instructionState = null;

    private Map<String, Literal> variableMap = new HashMap<>();

    protected InstructionState() {}

    public static InstructionState getInstance() {
        if (instructionState == null) {
            instructionState = new InstructionState();
        }
        return instructionState;
    }

    public void setComment(boolean set) {
        inComment = set;
    }

    public boolean getComment() {
        return inComment;
    }

    public void setVariable(String name, Literal value) {
        variableMap.put(name, value);
    }

    public Literal getVariable(String name) {
        return variableMap.get(name);
    }

    public boolean hasVariable(String name) {
        return variableMap.containsKey(name) && variableMap.get(name) != null;
    }
}
