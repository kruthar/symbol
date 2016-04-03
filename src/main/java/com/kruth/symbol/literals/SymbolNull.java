package com.kruth.symbol.literals;

import javax.naming.OperationNotSupportedException;

/**
 * Created by kruthar on 4/3/16.
 */
public class SymbolNull extends Literal {
    public String toString() {
        return "null";
    }

    @Override
    public Literal times(Literal other) throws OperationNotSupportedException {
        return null;
    }

    @Override
    public Literal dividedby(Literal other) throws OperationNotSupportedException {
        return null;
    }

    @Override
    public Literal modulo(Literal other) throws OperationNotSupportedException {
        return null;
    }

    @Override
    public Literal plus(Literal other) {
        return null;
    }

    @Override
    public Literal minus(Literal other) throws OperationNotSupportedException {
        return null;
    }

    @Override
    public int comparedTo(Literal other) throws OperationNotSupportedException {
        return 0;
    }
}
