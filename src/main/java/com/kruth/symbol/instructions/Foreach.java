package com.kruth.symbol.instructions;

import com.kruth.symbol.ErrorState;
import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.structures.Structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kruthar on 4/9/16.
 */
public class Foreach {
    public static void parse(InstructionState parentState, String instruction, Boolean execute) throws VariableDoesNotExistsException {
        SpaceLexer lexer = new SpaceLexer(instruction);

        if (lexer.hasNext()) {
            String var = lexer.next();

            if (lexer.hasNext() && lexer.next().equals("in")) {
                if (lexer.hasNext()) {
                    SymbolObject list = new Expression(parentState, lexer.advancedTo("do")).evaluate();
                    List<String> lines = parentState.advanceTo("end");

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
                        } else {
                            System.out.println("ERROR: Foreach loop expects input of type Structure");
                        }
                        ErrorState.incrementLine();
                    }
                } else {
                    System.out.println("ERROR: Expecting some type of Structure as input for foreach loop");
                }
            } else {
                System.out.println("ERROR: Expeing the keyword 'in' in foreach loop");
            }
        } else {
            System.out.println("ERROR: Expecting the rest of a foreach expression");
        }
    }
}
