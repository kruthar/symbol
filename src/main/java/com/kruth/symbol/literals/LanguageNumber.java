package com.kruth.symbol.literals;

import com.kruth.symbol.LanguageObject;

import java.math.BigDecimal;

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

    public SymbolNumber ceil() {
        return new SymbolNumber(((BigDecimal) parent.getValue()).setScale(0, BigDecimal.ROUND_CEILING));
    }

    public SymbolNumber floor() {
        return new SymbolNumber(((BigDecimal) parent.getValue()).setScale(0, BigDecimal.ROUND_FLOOR));
    }
}
