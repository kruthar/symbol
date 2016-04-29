package com.kruth.symbol.literals;

import com.kruth.symbol.LanguageObject;
import com.kruth.symbol.SymbolObject;

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
    public Object getValue() {
        return null;
    }

    @Override
    public LanguageObject getLanguageObject() {
        return new LanguageNull();
    }

    @Override
    public int compareTo(SymbolObject other) {
        if (other instanceof SymbolNull) {
            return 0;
        }
        return -1;
    }
}
