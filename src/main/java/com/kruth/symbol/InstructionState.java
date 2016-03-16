package com.kruth.symbol;

/**
 * Created by kruthar on 3/15/16.
 */
public class InstructionState {
    private boolean inComment = false;
    private static InstructionState instructionState = null;

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
}
