package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.lexers.SpaceLexer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kruthar on 4/8/16.
 */
public class For {
    public static void parse(InstructionState parentState, String instruction, Boolean execute) {
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
                    String incrementInstruction = lexer.advancedTo("do");

                    List<String> lines = new ArrayList<>();
                    while (parentState.hasNextLine()) {
                        String nextLine = parentState.nextLine();
                        String[] nextSplit = nextLine.trim().split(" ");
                        if (!nextSplit[0].equals("end")) {
                            lines.add(nextLine);
                        } else {
                            // once we hit 'end' then break
                            break;
                        }
                    }

                    instructionState.setLineLexerList(lines);

                    // At this point we have lexed passed the lines of the loop, if we do not need to execute then just terminate.
                    // Else wise, start executing the loop
                    if (execute) {
                        System.out.println("lets execute");
                    }
                } else {
                    System.out.println("ERROR: Expecting a variable declaration as the final part of the for loop");
                }
            } else {
                System.out.println("ERROR: Expecting while boolean expression as second part of for loop");
            }
        } else {
            System.out.println("ERROR: Expecting variable declaration as first part of 'for' loop");
        }
    }
}
