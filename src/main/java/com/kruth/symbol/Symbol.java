package com.kruth.symbol;

import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;

/**
 * Created by kruthar on 2/23/16.
 */
public class Symbol {
    private static InstructionState instructionState = InstructionState.getInstance();
    private static InstructionRouter instructionRouter = InstructionRouter.getInstance();

    public static void main(String args[]) {
        Symbol.executeFile("test.symb");
    }

    public static void executeFile(String filename) {
        instructionState.setLineLexerFile(filename);
        execute();
    }

    public static void executeLine(String line) {
        instructionState.setLineLexerString(line);
        execute();
    }

    public static void execute() {

        while (instructionState.hasNextLine()) {
            instructionRouter.routeNextInstruction(true);
        }
    }
}
