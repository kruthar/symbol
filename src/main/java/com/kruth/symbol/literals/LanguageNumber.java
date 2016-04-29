package com.kruth.symbol.literals;

import com.kruth.symbol.LanguageObject;

/**
 * Created by kruthar on 4/28/16.
 */
public class LanguageNumber implements LanguageObject {
    private SymbolNumber parent;

    public LanguageNumber(SymbolNumber parent) {
        this.parent = parent;
    }

    public SymbolString tostring() {
        return new SymbolString(parent.getValue().toString());
    }
}
