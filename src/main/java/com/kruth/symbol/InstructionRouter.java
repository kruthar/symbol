package com.kruth.symbol;

import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.instructions.Comment;
import com.kruth.symbol.instructions.Instruction;
import com.kruth.symbol.instructions.Print;
import com.kruth.symbol.lexers.LineLexer;

/**
 * Created by kruthar on 2/24/16.
 */
public class InstructionRouter {
    public static Instruction getInstruction(String instruction) {
        InstructionState instructionState = InstructionState.getInstance();
        String[] instructionSplit = instruction.split(" ", 2);

        if (instructionState.getComment()) {
            if (instructionSplit[0] == "blockcomment") {
                instructionState.setComment(false);
            }
            return new Comment(instruction);
        }

        switch (instructionSplit[0].toLowerCase()) {
            case "blockcomment":
                instructionState.setComment(true);
            case "comment":
                return new Comment(instruction);
            case "print":
                return new Print(instructionSplit[1]);
            case "set":
                String[] setSplit = instructionSplit[1].split(" ", 2);
                instructionState.setVariable(setSplit[0], new Expression(setSplit[1]).evaluate());
                break;
            default:
                System.out.println("Unknown instruction '" + instructionSplit[0] + "'");
        }

        return null;
    }
}
