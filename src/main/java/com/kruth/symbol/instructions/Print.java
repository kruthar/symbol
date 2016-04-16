package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.Literal;

/**
 * Created by kruthar on 2/23/16.
 */
public class Print {
    public static void parse(InstructionState instructionState, String line, Boolean execute) throws VariableDoesNotExistsException {
        if (execute) {
            SpaceLexer printLexer = new SpaceLexer(line.trim());

            // lex out print
            printLexer.next();

            String printable = "";
            if (printLexer.hasNext()) {
                Expression expression = new Expression(instructionState, printLexer);
                SymbolObject symbolObject = expression.evaluate();
                printable = symbolObject.toString();
            }
            System.out.print(printable);
        }
    }
}
