package com.kruth.symbol.structures;

import com.kruth.symbol.SymbolObject;

/**
 * Created by kruthar on 4/5/16.
 */
public abstract class Structure implements SymbolObject {
    public abstract SymbolObject plus(SymbolObject other);

    public abstract int compareTo(SymbolObject other);
}
