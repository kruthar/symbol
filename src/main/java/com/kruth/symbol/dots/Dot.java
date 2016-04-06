package com.kruth.symbol.dots;

import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.expression.ExpressionComponent;

import java.util.List;

/**
 * Created by kruthar on 4/5/16.
 */
public class Dot implements ExpressionComponent {
    private String name;
    private List<Expression> parameters;

    public Dot(String name, List<Expression> parameters) {
        this.name = name;
        this.parameters = parameters;
    }
}
