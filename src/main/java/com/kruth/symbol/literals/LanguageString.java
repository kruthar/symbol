package com.kruth.symbol.literals;

import com.kruth.symbol.LanguageObject;
import com.kruth.symbol.exceptions.IndexOutOfBoundsException;
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

    public SymbolString substring(SymbolNumber start) throws SymbolException{
        String numstring = (String) parent.getValue();

        if (numstring.length() <= ((BigDecimal) start.getValue()).intValue()) {
            throw new IndexOutOfBoundsException("Start index: " + start.getValue() + " out of bounds of string length: " + numstring.length());
        }
        return new SymbolString(numstring.substring(((BigDecimal) start.getValue()).intValue()));
    }

    public SymbolString substring(SymbolNumber start, SymbolNumber end) throws SymbolException{
        String numstring = (String) parent.getValue();

        if (numstring.length() <= ((BigDecimal) start.getValue()).intValue()) {
            throw new IndexOutOfBoundsException("Start index: " + start.getValue() + " out of bounds of string length: " + numstring.length());
        }

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

    public SymbolNumber indexof(SymbolString needle) {
        return new SymbolNumber(((String) parent.getValue()).indexOf((String) needle.getValue()));
    }

    public SymbolString replace(SymbolString find, SymbolString replace) {
        return new SymbolString(((String) parent.getValue()).replace((String) find.getValue(), (String) replace.getValue()));
    }
}
