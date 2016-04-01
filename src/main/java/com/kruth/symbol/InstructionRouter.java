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

    public static void routeNextInstruction() {
        String instruction = instructionState.nextLine();
        String[] instructionSplit = instruction.split(" ", 2);

        switch (instructionSplit[0].toLowerCase()) {
            case "blockcomment":
                BlockComment.parse(instruction);
                break;
            case "comment":
                Comment.parse(instruction);
                break;
            case "print":
                Print.parse(instructionSplit[1]);
                break;
            case "println":
                Println.parse(instructionSplit[1]);
                break;
            default:
                String[] variableSplit = instructionSplit[1].split(" ", 2);
                if (variableSplit[0].equals("is")) {
                    instructionState.setVariable(instructionSplit[0], new Expression(variableSplit[1]).evaluate());
                } else {
                    System.out.println("Unknown instruction '" + instructionSplit[0] + "'");
                }
        }
    }
}
