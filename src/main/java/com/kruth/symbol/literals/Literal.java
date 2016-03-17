package com.kruth.symbol.literals;

import com.kruth.symbol.expression.ExpressionComponent;

/**
 * Created by kruthar on 2/24/16.
 */
public interface Literal extends ExpressionComponent {
    String toString();
    Literal plus(Literal other);
}
