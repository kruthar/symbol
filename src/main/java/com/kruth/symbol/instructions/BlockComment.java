package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;

/**
 * Created by kruthar on 3/31/16.
 */
public class BlockComment {
    private static InstructionState instructionState = InstructionState.getInstance();

    public static void parse(String line) {
        String[] instructionSplit = instructionState.nextLine().split(" ", 2);

        while (!instructionSplit[0].equals("blockcomment")) {
            // Still inside of the blockcomment, keep lexing.
            instructionSplit = instructionState.nextLine().split(" ", 2);
        }
    }
}
