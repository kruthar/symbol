package com.kruth.symbol.instructions;

import com.kruth.symbol.expression.Expression;

/**
 * Created by kruthar on 2/23/16.
 */
public class Print implements Instruction {
    private Expression expression;

    public Print (String expression) {
        this.expression = new Expression(expression);
    }

    public void runInstruction() {
        System.out.println(expression.toString());
    }
}
