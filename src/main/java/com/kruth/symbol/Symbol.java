package com.kruth.symbol;

import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;

import java.io.File;

/**
 * Created by kruthar on 2/23/16.
 */
public class Symbol {
    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Symbol requires a file path.");
        }

        try {
            Symbol.executeFile(args[0]);
        } catch (SymbolException e) {
            e.printStackTrace();
        }
    }

    public static void executeFile(String filename) throws SymbolException {
        InstructionState instructionState = new InstructionState();
        instructionState.setLineLexerFile(filename);
        ErrorState.setFile(filename);
        execute(instructionState);
    }

    public static void executeLine(String line) throws SymbolException {
        InstructionState instructionState = new InstructionState();
        instructionState.setLineLexerString(line);
        execute(instructionState);
    }

    public static void execute(InstructionState instructionState) throws SymbolException {
        while (instructionState.hasNextLine()) {
            instructionState.routeNextInstruction(true);
        }
    }
}
