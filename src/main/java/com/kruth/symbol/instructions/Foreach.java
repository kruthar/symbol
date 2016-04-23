package com.kruth.symbol.instructions;

import com.kruth.symbol.ErrorState;
import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.UnexpectedKeywordException;
import com.kruth.symbol.exceptions.UnexpectedStateException;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.structures.Structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kruthar on 4/9/16.
 */
public class Foreach {
    public static void parse(InstructionState parentState, String instruction, Boolean execute) throws SymbolException {
        SpaceLexer lexer = new SpaceLexer(instruction);

        if (lexer.hasNext()) {
            String var = lexer.next();

            if (lexer.hasNext() && lexer.peek().equals("in")) {
                lexer.next();
                if (lexer.hasNext()) {
                    SymbolObject list = new Expression(parentState, lexer.advancedTo("do")).evaluate();
                    List<String> lines = parentState.advanceToLoopScoped();

                    if (execute) {
                        if (list instanceof Structure) {
                            InstructionState instructionState = new InstructionState();
                            instructionState.setParentState(parentState);
                            instructionState.setLineLexerList(lines);
                            instructionState.pushCurrentLoopMarker();
                            Iterator<SymbolObject> iter = ((Structure) list).getIterator();
                            while (iter.hasNext()) {
                                instructionState.resetToCurrentLoopMarker();
                                instructionState.setVariable(var, iter.next());

                                while (instructionState.hasNextLine()) {
                                    instructionState.routeNextInstruction(execute);
                                }
                            }
                            parentState.setReturnValue(instructionState.getReturnValue());
                        } else {
                            throw new UnexpectedStateException("Foreach loop expects a structure as input.");
                        }
                        ErrorState.incrementLine();
                    }
                } else {
                    throw new UnexpectedKeywordException("Expecting structure for loop, found end of instruction.");
                }
            } else {
                throw new UnexpectedKeywordException("Expecting the keyword 'in', found: " + lexer.peek());
            }
        } else {
            throw new UnexpectedKeywordException("Expecting variable name, found end of instruction.");
        }
    }
}
