package com.kruth.symbol;

/**
 * Created by kruthar on 2/23/16.
 */
public class Symbol {
    public static void main(String args[]) {
        Symbol.executeFile("test.symb");
    }

    public static void executeFile(String filename) {
        InstructionState instructionState = new InstructionState();
        instructionState.setLineLexerFile(filename);
        execute(instructionState);
    }

    public static void executeLine(String line) {
        InstructionState instructionState = new InstructionState();
        instructionState.setLineLexerString(line);
        execute(instructionState);
    }

    public static void execute(InstructionState instructionState) {
        while (instructionState.hasNextLine()) {
            instructionState.routeNextInstruction(true);
        }
    }
}
