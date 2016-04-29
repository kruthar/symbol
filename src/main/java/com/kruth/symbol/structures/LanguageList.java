package com.kruth.symbol.structures;

import com.kruth.symbol.LanguageObject;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.SymbolListIndexOutOfBoundsException;
import com.kruth.symbol.literals.SymbolNumber;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by kruthar on 4/28/16.
 */
public class LanguageList implements LanguageObject {
    private SymbolList parent;

    public LanguageList(SymbolList parent) {
        this.parent = parent;
    }

    public void put(SymbolObject obj) {
        List<SymbolObject> value = ((List<SymbolObject>) parent.getValue());
        value.add(obj);
    }

    public void put(SymbolNumber index, SymbolObject obj) throws SymbolException {
        List<SymbolObject> value = ((List<SymbolObject>) parent.getValue());
        try {
            value.add(((BigDecimal) index.getValue()).intValue(), obj);
        } catch (IndexOutOfBoundsException e) {
            throw new SymbolListIndexOutOfBoundsException("Attempting to insert at index " + index + ", but list size is only " + value.size() + ".", e);
        }
    }

    public SymbolObject get(SymbolNumber index) throws SymbolException{
        List<SymbolObject> value = ((List<SymbolObject>) parent.getValue());
        try {
            return value.get(((BigDecimal) index.getValue()).intValue());
        } catch (IndexOutOfBoundsException e) {
            throw new SymbolListIndexOutOfBoundsException("Attempting to get index " + index + ", but list size is only " + value.size() + ".", e);
        }
    }

    public SymbolNumber size() {
        List<SymbolObject> value = ((List<SymbolObject>) parent.getValue());
        return new SymbolNumber(value.size());
    }

    public void remove(SymbolNumber index) throws SymbolException {
        List<SymbolObject> value = ((List<SymbolObject>) parent.getValue());
        try {
            value.remove(((BigDecimal) index.getValue()).intValue());
        } catch (IndexOutOfBoundsException e) {
            throw new SymbolListIndexOutOfBoundsException("Attempting to remove index " + index + ", but list size is only " + value.size() + ".", e);
        }
    }
}
