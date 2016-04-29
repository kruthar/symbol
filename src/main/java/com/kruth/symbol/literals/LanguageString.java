package com.kruth.symbol.literals;

import com.kruth.symbol.LanguageObject;

/**
 * Created by kruthar on 4/28/16.
 */
public class LanguageString implements LanguageObject {
    private SymbolString parent;

    public LanguageString(SymbolString parent) {
        this.parent = parent;
    }

    public SymbolNumber length() {
        return new SymbolNumber(((String) parent.getValue()).length());
    }
}
