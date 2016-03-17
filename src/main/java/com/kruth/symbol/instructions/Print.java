package com.kruth.symbol.instructions;

import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.literals.Literal;

/**
 * Created by kruthar on 2/23/16.
 */
public class Print implements Instruction {
    private Expression expression;

    public Print (String expression) {
        this.expression = new Expression(expression);
    }

    public void runInstruction() {
        Literal literal = expression.evaluate();
        System.out.println(literal.toString());
    }
}
