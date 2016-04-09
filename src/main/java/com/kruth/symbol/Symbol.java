package com.kruth.symbol;

import com.kruth.symbol.exceptions.VariableDoesNotExistsException;

/**
 * Created by kruthar on 2/23/16.
 */
public class Symbol {
    public static void main(String args[]) {
        try {
            Symbol.executeFile("test.symb");
        } catch (VariableDoesNotExistsException e) {
            System.err.println("Variable does not exist.");
            e.printStackTrace();
        }
    }

    public static void executeFile(String filename) throws VariableDoesNotExistsException {
        InstructionState instructionState = new InstructionState();
        instructionState.setLineLexerFile(filename);
        execute(instructionState);
    }

    public static void executeLine(String line) throws VariableDoesNotExistsException {
        InstructionState instructionState = new InstructionState();
        instructionState.setLineLexerString(line);
        execute(instructionState);
    }

    public static void execute(InstructionState instructionState) throws VariableDoesNotExistsException {
        while (instructionState.hasNextLine()) {
            instructionState.routeNextInstruction(true);
        }
    }
}
