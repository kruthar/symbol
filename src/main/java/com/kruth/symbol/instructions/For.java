package com.kruth.symbol.instructions;

import com.kruth.symbol.ErrorState;
import com.kruth.symbol.InstructionState;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.UnexpectedKeywordException;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.SymbolBoolean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kruthar on 4/8/16.
 */
public class For {
    public static void parse(InstructionState parentState, String instruction, Boolean execute) throws SymbolException {
        InstructionState instructionState = new InstructionState();
        instructionState.setParentState(parentState);
        SpaceLexer lexer = new SpaceLexer(instruction);

        if (lexer.peek().equals("variable")) {
            String variableInstruction = lexer.advancedTo("while");

            if (lexer.peek().equals("while")) {
                // lex out 'while'
                lexer.next();
                String whileExpression = lexer.advancedTo("with");

                if (lexer.peek().equals("with")) {
                    // lex out 'with'
                    lexer.next();
                    String incrementInstruction = lexer.advancedTo("do");
                    List<String> lines = parentState.advanceToLoopScoped();

                    instructionState.setLineLexerList(lines);

                    // At this point we have lexed passed the lines of the loop, if we do not need to execute then just terminate.
                    // Else wise, start executing the loop
                    if (execute) {
                        // first step is to execute the variable declaration
                        instructionState.routeInstruction(variableInstruction, execute);
                        instructionState.pushCurrentLoopMarker();

                        Expression condition = new Expression(instructionState, whileExpression);

                        while ((Boolean) condition.evaluate().getValue()) {
                            // Execute lines
                            instructionState.resetToCurrentLoopMarker();
                            while (instructionState.hasNextLine()) {
                                instructionState.routeNextInstruction(execute);
                            }
                            // increment variable
                            instructionState.routeInstruction(incrementInstruction, execute);
                        }
                        parentState.setReturnValue(instructionState.getReturnValue());
                        ErrorState.incrementLine();
                    }
                } else {
                    throw new UnexpectedKeywordException("Expecting the keyword 'with', found: " + lexer.peek());
                }
            } else {
                throw new UnexpectedKeywordException("Expecting the keyword 'while', found: " + lexer.peek());
            }
        } else {
            throw new UnexpectedKeywordException("Expecting the keyword 'variable', found: " + lexer.peek());
        }
    }
}
