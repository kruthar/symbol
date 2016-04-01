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
            case "set":
                String[] setSplit = instructionSplit[1].split(" ", 2);
                instructionState.setVariable(setSplit[0], new Expression(setSplit[1]).evaluate());
                break;
            default:
                System.out.println("Unknown instruction '" + instructionSplit[0] + "'");
        }
    }
}
