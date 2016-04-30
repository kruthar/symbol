package com.kruth.symbol.literals;

import com.kruth.symbol.LanguageObject;

import java.math.BigDecimal;

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

    public SymbolString substring(SymbolNumber start) {
        return new SymbolString(((String) parent.getValue()).substring(((BigDecimal) start.getValue()).intValue()));
    }

    public SymbolString substring(SymbolNumber start, SymbolNumber end) {
        return new SymbolString(((String) parent.getValue()).substring(((BigDecimal) start.getValue()).intValue(), ((BigDecimal) end.getValue()).intValue()));
    }
}
