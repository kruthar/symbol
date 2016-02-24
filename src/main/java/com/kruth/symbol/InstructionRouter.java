package com.kruth.symbol;

import com.kruth.symbol.instructions.Instruction;
import com.kruth.symbol.instructions.Print;
import com.kruth.symbol.lexers.LineLexer;

/**
 * Created by kruthar on 2/24/16.
 */
public class InstructionRouter {
    public static Instruction getInstruction(LineLexer lexer, String instruction) {
        String[] instructionSplit = sanitizeInstruction(instruction).split(" ", 2);

        switch (instructionSplit[0]) {
            case "print":
                return new Print(instructionSplit[1]);
            default:
                System.out.println("Unknown instruction '" + instructionSplit[0] + "'");
                return null;
        }
    }

    public static String sanitizeInstruction(String instruction) {
        return instruction.toLowerCase();
    }
}
