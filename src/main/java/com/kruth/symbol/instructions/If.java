package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.literals.Literal;
import com.kruth.symbol.literals.SymbolBoolean;

/**
 * Created by kruthar on 3/31/16.
 */
public class If {
    public static void parse(InstructionState instructionState, String line, Boolean execute) {
        Expression conditionalExpression = new Expression(instructionState, line);
        Literal conditional = conditionalExpression.evaluate();
        boolean conditionTrue = (Boolean) conditional.getValue();

        String nextInstruction = instructionState.peekNextLine();
        String[] instructionSplit = nextInstruction.trim().split(" ", 2);

        while (!instructionSplit[0].equals("fi") && !instructionSplit[0].equals("else")) {
            // Still inside of the if block, route this instruction
            instructionState.routeNextInstruction(execute && conditionTrue);
            nextInstruction = instructionState.peekNextLine();
            instructionSplit = nextInstruction.trim().split(" ", 2);
        }

        if (instructionSplit[0].equals("else")) {
            String elseLine = instructionState.nextLine();
            String[] elseSplit = elseLine.split(" ", 3);
            if (elseSplit.length > 2 && elseSplit[1].equals("if")) {
                If.parse(instructionState, elseSplit[2], execute && !conditionTrue);
            } else {
                while (!instructionSplit[0].equals("fi")) {
                    // Still inside of the if block, route this instruction
                    instructionState.routeNextInstruction(execute && !conditionTrue);
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
