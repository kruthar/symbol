package com.kruth.symbol.literals;

import com.kruth.symbol.expression.ExpressionComponent;

import javax.naming.OperationNotSupportedException;

/**
 * Created by kruthar on 2/24/16.
 */
public abstract class Literal implements ExpressionComponent {
    public abstract Literal times(Literal other) throws OperationNotSupportedException;
    public abstract Literal dividedby(Literal other) throws OperationNotSupportedException;
    public abstract Literal modulo(Literal other) throws OperationNotSupportedException;
    public abstract Literal plus(Literal other);
    public abstract Literal minus(Literal other) throws OperationNotSupportedException;
    public abstract int comparedTo(Literal other) throws OperationNotSupportedException;

    public Literal equalTo(Literal other) throws OperationNotSupportedException {
        return new SymbolBoolean(this.comparedTo(other) == 0);
    };
    public Literal notEqualTo(Literal other) throws OperationNotSupportedException {
        return new SymbolBoolean(!((SymbolBoolean) this.equalTo(other)).getValue());
    }
}
