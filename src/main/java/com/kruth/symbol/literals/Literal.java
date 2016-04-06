package com.kruth.symbol.literals;

import com.kruth.symbol.expression.ExpressionComponent;

import javax.naming.OperationNotSupportedException;

/**
 * Created by kruthar on 2/24/16.
 */
public abstract class Literal implements ExpressionComponent, Comparable<Literal> {
    public abstract Literal times(Literal other) throws OperationNotSupportedException;
    public abstract Literal dividedby(Literal other) throws OperationNotSupportedException;
    public abstract Literal modulo(Literal other) throws OperationNotSupportedException;
    public abstract Literal plus(Literal other);
    public abstract Literal minus(Literal other) throws OperationNotSupportedException;

    public Literal greaterThan(Literal other) {
        return new SymbolBoolean(this.compareTo(other) > 0);
    }

    public Literal greaterThanOrEqualTo(Literal other) {
        int compared = this.compareTo(other);
        return new SymbolBoolean(compared >= 0);
    }

    public Literal lessThan(Literal other) {
        return new SymbolBoolean(this.compareTo(other) < 0);
    }

    public Literal lessThanOrEqualTo(Literal other) {
        int compared = this.compareTo(other);
        return new SymbolBoolean(compared <= 0);
    }

    public abstract Object getValue();

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        Literal sl = (Literal) o;

        return this.getValue().equals(((Literal) o).getValue());
    }

    public abstract int compareTo(Literal other);
}
