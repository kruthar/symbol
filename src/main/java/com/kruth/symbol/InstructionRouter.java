package com.kruth.symbol;

import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.instructions.*;

/**
 * Created by kruthar on 2/24/16.
 */
public class InstructionRouter {
    private static InstructionState instructionState = InstructionState.getInstance();
    private static InstructionRouter instructionRouter = null;

    protected InstructionRouter() {}

    public static InstructionRouter getInstance() {
        if (instructionRouter == null) {
            instructionRouter = new InstructionRouter();
        }
        return instructionRouter;
    }

    public static void routeNextInstruction(Boolean execute) {
        String instruction = instructionState.nextLine();

        // Return if we have an empty line
        if (instruction.trim().length() < 1) {
            return;
        }

        String[] instructionSplit = instruction.trim().split(" ", 2);

        switch (instructionSplit[0].toLowerCase()) {
            case "blockcomment":
                BlockComment.parse(instruction, execute);
                break;
            case "comment":
                Comment.parse(instruction, execute);
                break;
            case "print":
                Print.parse(instructionSplit[1], execute);
                break;
            case "println":
                Println.parse(instructionSplit[1], execute);
                break;
            case "if":
                If.parse(instructionSplit[1], execute);
                break;
            default:
                String[] variableSplit = instructionSplit[1].split(" ", 2);
                if (variableSplit[0].equals("is")) {
                    if (execute) {
                        instructionState.setVariable(instructionSplit[0], new Expression(variableSplit[1]).evaluate());
                    }
                } else {
                    System.out.println("Unknown instruction '" + instructionSplit[0] + "'");
                }
        }
    }
}
