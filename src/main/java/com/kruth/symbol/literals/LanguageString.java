package com.kruth.symbol.literals;

import com.kruth.symbol.LanguageObject;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.lexers.SpaceLexer;

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

    public SymbolNumber tonumber() throws SymbolException{
        String numstring = (String) parent.getValue();
        SymbolNumber num;
        try {
            num = new SymbolNumber(new BigDecimal(numstring));
        } catch (NumberFormatException e) {
            num = new SymbolNumber(new SpaceLexer(numstring));
        }
        return num;
    }
}
