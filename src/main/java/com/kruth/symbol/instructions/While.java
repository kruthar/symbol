package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionRouter;
import com.kruth.symbol.InstructionState;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.Literal;
import com.kruth.symbol.literals.SymbolBoolean;

import java.rmi.UnexpectedException;

/**
 * Created by kruthar on 4/1/16.
 */
public class While {
    private static InstructionState instructionState = InstructionState.getInstance();
    private static InstructionRouter instructionRouter = InstructionRouter.getInstance();

    public static void parse(String line, Boolean execute) {
        Expression conditionExpression = new Expression(new SpaceLexer(line));
        Boolean condition = false;

        try {
            condition = getConditionValue(conditionExpression);
        } catch (UnexpectedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        instructionState.pushCurrentLoopMarker();

        while (condition) {
            instructionState.resetToCurrentLoopMarker();

            String nextInstruction = instructionState.peekNextLine();
            String[] instructionSplit = nextInstruction.trim().split(" ", 2);

            while (!instructionSplit[0].equals("end")) {
                // Still inside of the while block, route this instruction
                instructionRouter.routeNextInstruction(execute);
                nextInstruction = instructionState.peekNextLine();
                instructionSplit = nextInstruction.trim().split(" ", 2);
            }

            try {
                condition = getConditionValue(conditionExpression);
            } catch (UnexpectedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        // Lex out the 'end' line, then pop off the loopmarker
        instructionState.nextLine();
        instructionState.popCurrentLoopMarker();
    }

    private static Boolean getConditionValue(Expression conditionExpression) throws UnexpectedException {
        Literal condition = conditionExpression.evaluate();

        if (!(condition instanceof SymbolBoolean)) {
            throw new UnexpectedException("Expecting a SymbolBoolean expression in while condition, recieved: " + condition.getClass());
        }

        return ((SymbolBoolean) condition).getValue();
    }
}
