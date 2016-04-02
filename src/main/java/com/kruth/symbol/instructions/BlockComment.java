package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;

/**
 * Created by kruthar on 3/31/16.
 */
public class BlockComment {
    public static void parse(InstructionState instructionState, String line, Boolean execute) {
        String[] instructionSplit = instructionState.nextLine().split(" ", 2);

        while (!instructionSplit[0].equals("blockcomment")) {
            // Still inside of the blockcomment, keep lexing.
            instructionSplit = instructionState.nextLine().split(" ", 2);
        }
    }
}
