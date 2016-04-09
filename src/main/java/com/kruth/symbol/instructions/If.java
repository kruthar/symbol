package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kruthar on 3/31/16.
 */
public class If {
    public static void parse(InstructionState parentState, String line, Boolean execute) throws VariableDoesNotExistsException {
        SpaceLexer lexer = new SpaceLexer(line);

        if (lexer.hasNext()) {
            String conditionString = lexer.advancedTo("");
            List<String> lines = parentState.advanceTo("fi");

            if (execute) {
                InstructionState instructionState = new InstructionState();
                instructionState.setParentState(parentState);
                instructionState.setLineLexerList(lines);

                // whether or not to execute the current block, determined by result of if condition
                Boolean executeBlock = (Boolean) new Expression(parentState, conditionString).evaluate().getValue();
                // whether we have already executed an if/else block
                Boolean ifExecuted = false;

                while (instructionState.hasNextLine()) {
                    String next = instructionState.peekNextLine();
                    String[] nextSplit = next.trim().split(" ", 3);

                    if (nextSplit[0].equals("else")) {
                        // flip the ifExecuted if the previous block was being executed.
                        if (executeBlock) {
                            ifExecuted = true;
                        }

                        if (nextSplit.length == 2) {
                            System.out.println("ERROR: else if needs a condition expression");
                        } else if (nextSplit.length == 3 && nextSplit[1].equals("if")) {
                            executeBlock = (Boolean) new Expression(parentState, nextSplit[2]).evaluate().getValue();
                        } else {
                            executeBlock = !executeBlock;
                        }
                        instructionState.nextLine();
                    } else {
                        instructionState.routeNextInstruction(!ifExecuted && executeBlock);
                    }
                }
            }
        } else {
            System.out.println("ERROR: If statement expects a boolean expression");
        }
    }
}
