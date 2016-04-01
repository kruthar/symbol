package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionRouter;
import com.kruth.symbol.InstructionState;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.literals.Literal;
import com.kruth.symbol.literals.SymbolBoolean;

/**
 * Created by kruthar on 3/31/16.
 */
public class If {
    private static InstructionState instructionState = InstructionState.getInstance();
    private static InstructionRouter instructionRouter = InstructionRouter.getInstance();

    public static void parse(String line, Boolean execute) {
        Expression conditionalExpression = new Expression(line);
        Literal conditional = conditionalExpression.evaluate();
        boolean conditionTrue = ((SymbolBoolean) conditional).getValue();

        String nextInstruction = instructionState.peekNextLine();
        String[] instructionSplit = nextInstruction.split(" ", 2);

        while (!instructionSplit[0].equals("fi") && !instructionSplit[0].equals("else")) {
            // Still inside of the if block, route this instruction
            instructionRouter.routeNextInstruction(execute && conditionTrue);
            nextInstruction = instructionState.peekNextLine();
            instructionSplit = nextInstruction.split(" ", 2);
        }

        if (instructionSplit[0].equals("else")) {
            String elseLine = instructionState.nextLine();
            String[] elseSplit = elseLine.split(" ", 3);
            if (elseSplit.length > 2 && elseSplit[1].equals("if")) {
                If.parse(elseSplit[2], execute && !conditionTrue);
            } else {
                while (!instructionSplit[0].equals("fi")) {
                    // Still inside of the if block, route this instruction
                    instructionRouter.routeNextInstruction(execute && !conditionTrue);
                    nextInstruction = instructionState.peekNextLine();
                    instructionSplit = nextInstruction.split(" ", 2);
                }

                // Lex out the 'fi' line
                instructionState.nextLine();
            }
        } else {
            // Lex out the 'fi' line
            instructionState.nextLine();
        }
    }
}
