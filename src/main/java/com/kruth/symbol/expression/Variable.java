package com.kruth.symbol.expression;

/**
 * Created by kruthar on 4/1/16.
 */
public class Variable implements ExpressionComponent {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
}
