package com.kruth.symbol.literals;

import com.kruth.symbol.expression.ExpressionComponent;

/**
 * Created by kruthar on 2/24/16.
 */
public abstract class Literal implements ExpressionComponent {
    public abstract Literal times(Literal other);
    public abstract Literal dividedby(Literal other);
    public abstract Literal plus(Literal other);
    public abstract Literal minus(Literal other);
    public abstract int comparedTo(Literal other);

    public Literal equalTo(Literal other) {
        return new SymbolBoolean(this.comparedTo(other) == 0);
    };
    public Literal notEqualTo(Literal other) {
        return new SymbolBoolean(!((SymbolBoolean) this.equalTo(other)).getValue());
    }
}
