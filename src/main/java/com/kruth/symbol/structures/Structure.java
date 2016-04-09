package com.kruth.symbol.structures;

import com.kruth.symbol.SymbolObject;

import java.util.Iterator;

/**
 * Created by kruthar on 4/5/16.
 */
public abstract class Structure implements SymbolObject {
    public abstract int compareTo(SymbolObject other);

    public abstract Iterator<SymbolObject> getIterator();
}
