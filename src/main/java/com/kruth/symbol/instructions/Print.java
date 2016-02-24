package com.kruth.symbol.instructions;

import com.kruth.symbol.Expression;

/**
 * Created by kruthar on 2/23/16.
 */
public class Print implements Instruction {
    private Expression expression;

    public Print (String expression) {
        this.expression = Expression.parse(expression);
    }

    public void runInstruction() {
        System.out.println(expression.toString());
    }
}
