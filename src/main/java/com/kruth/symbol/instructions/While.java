package com.kruth.symbol.instructions;

import com.kruth.symbol.ErrorState;
import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.Literal;
import com.kruth.symbol.literals.SymbolBoolean;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kruthar on 4/1/16.
 */
public class While {
    public static void parse(InstructionState parentState, String line, Boolean execute) throws VariableDoesNotExistsException {
        SpaceLexer lexer = new SpaceLexer(line);

        if (lexer.hasNext()) {
            String conditionString = lexer.advancedTo("do");
            List<String> lines = parentState.advanceTo("end");

            if (execute) {
                InstructionState instructionState = new InstructionState();
                instructionState.setParentState(parentState);
                instructionState.setLineLexerList(lines);
                instructionState.pushCurrentLoopMarker();

                Expression conditionExpression = new Expression(instructionState, conditionString);

                while ((Boolean) conditionExpression.evaluate().getValue()) {
                    instructionState.resetToCurrentLoopMarker();

                    while (instructionState.hasNextLine()) {
                        instructionState.routeNextInstruction(execute);
                    }
                }
                ErrorState.incrementLine();
            }
        } else {
            System.out.println("ERROR: expecting a conditional expression after while");
        }
    }
}
