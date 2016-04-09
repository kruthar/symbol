package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.literals.Literal;

/**
 * Created by kruthar on 2/23/16.
 */
public class Print {
    public static void parse(InstructionState instructionState, String line, Boolean execute) throws VariableDoesNotExistsException {
        if (execute) {
            Expression expression = new Expression(instructionState, line);
            SymbolObject symbolObject = expression.evaluate();
            System.out.print(symbolObject.toString());
        }
    }
}
