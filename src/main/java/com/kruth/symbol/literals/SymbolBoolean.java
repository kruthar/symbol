package com.kruth.symbol.literals;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kruthar on 3/18/16.
 */
public class SymbolBoolean implements Literal {
    private static final Map<String, Boolean> KEYWORDS;
    static {
        Map<String, Boolean> aMap = new HashMap<>();
        aMap.put("true", true);
        aMap.put("false", false);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    private boolean value;

    public SymbolBoolean() {
        this(false);
    }

    public SymbolBoolean(String val) {
        this(Boolean.valueOf(val));
    }

    public SymbolBoolean(boolean val) {
        value = val;
    }

    public boolean getValue() {
        return value;
    }

    public String toString() {
        if (value) {
            return "true";
        }

        return "false";
    }

    @Override
    public Literal times(Literal other) {
        System.out.println("ERROR: SymbolBoolean does not support times operation");
        return new SymbolBoolean(false);
    }

    @Override
    public Literal dividedby(Literal other) {
        System.out.println("ERROR: SymbolBoolean does not support dividedby operation");
        return new SymbolBoolean(false);
    }

    @Override
    public Literal plus(Literal other) {
        System.out.println("ERROR: SymbolBoolean does not support plus operation");
        return new SymbolBoolean(false);
    }

    @Override
    public Literal minus(Literal other) {
        System.out.println("ERROR: SymbolBoolean does not support minus operation");
        return new SymbolBoolean(false);
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }
}
