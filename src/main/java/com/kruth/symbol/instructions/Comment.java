package com.kruth.symbol.instructions;

/**
 * Created by kruthar on 3/15/16.
 */
public class Comment implements Instruction{
    private String commentString = "";

    public Comment() {
        this("");
    }

    public Comment(String comment) {
        commentString = comment;
    }

    @Override
    public void runInstruction() {

    }
}
