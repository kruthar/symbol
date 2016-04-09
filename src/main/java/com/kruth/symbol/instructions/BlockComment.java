package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;

/**
 * Created by kruthar on 3/31/16.
 */
public class BlockComment {
    public static void parse(InstructionState instructionState, Boolean execute) throws VariableDoesNotExistsException {
        Boolean found = false;
        while (!found) {
            SpaceLexer lexer = new SpaceLexer(instructionState.nextLine());

            while (lexer.hasNext()) {
                if (lexer.next().equals("blockcomment")) {
                    found = true;
                    instructionState.routeLexerInstruction(lexer, execute);
                }
            }
        }
    }

    public static void midParse(InstructionState instructionState, SpaceLexer lexer) {
        while (lexer.hasNext()) {
            if (lexer.peek().equals("blockcomment")) {
                lexer.next();
                return;
            }
        }

        // If we get to the end of the line, set a flag in instructionState that we need to continue the blockcomment after evaluating the last expression
        instructionState.setContinueBlockComment(true);
    }
}
