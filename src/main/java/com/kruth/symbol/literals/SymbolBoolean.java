package com.kruth.symbol.literals;

import com.kruth.symbol.lexers.SpaceLexer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kruthar on 3/18/16.
 */
public class SymbolBoolean extends Literal {
    private static final Map<String, Boolean> KEYWORDS;
    static {
        Map<String, Boolean> aMap = new HashMap<>();
        aMap.put("not", false);
        aMap.put("true", true);
        aMap.put("false", false);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    private boolean value;

    public SymbolBoolean() {
        this(false);
    }

    public SymbolBoolean(boolean val) {
        value = val;
    }

    public SymbolBoolean(SpaceLexer lexer) {
        boolean modifier = true;
        while (lexer.peek().equals("not")) {
            modifier = !modifier;
            lexer.next();
        }

        value = Boolean.valueOf(lexer.next()) == modifier;
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

    @Override
    public int comparedTo(Literal other) {
        if (!(other instanceof SymbolBoolean)) {
            System.out.println("ERROR: Cannot compare SymbolBoolean to " + other.getClass());
            System.exit(1);
        }

        return Boolean.compare(value, ((SymbolBoolean) other).getValue());
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }
}
