package com.kruth.symbol.literals;

/**
 * Created by kruthar on 2/24/16.
 */
public class SymbolString implements Literal {
    private String value;

    public SymbolString() {
        this("");
    }

    public SymbolString(String s) {
        value = s;
    }

    public void append(String a) {
        value += a;
    }

    public String toString() {
        return value;
    }
}
