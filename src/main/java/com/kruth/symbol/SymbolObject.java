package com.kruth.symbol;

import com.kruth.symbol.expression.ExpressionComponent;

/**
 * Created by kruthar on 4/5/16.
 */
public interface SymbolObject extends ExpressionComponent, Comparable<SymbolObject> {
    Object getValue();
}
