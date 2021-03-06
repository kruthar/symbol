package com.kruth.symbol.literals;

import com.kruth.symbol.LanguageObject;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.lexers.SpaceLexer;

import javax.naming.OperationNotSupportedException;
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

    public SymbolBoolean(SpaceLexer lexer) {
        Boolean modifier = true;
        while (lexer.peek().toLowerCase().equals("not")) {
            modifier = !modifier;
            lexer.next();
        }
        value = Boolean.valueOf(lexer.next()) == modifier;
    }

    public SymbolBoolean(boolean val) {
        value = val;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public LanguageObject getLanguageObject() {
        return new LanguageBoolean();
    }

    @Override
    public String toString() {
        if (value) {
            return "true";
        }

        return "false";
    }

    @Override
    public Literal times(Literal other) {
        return new SymbolBoolean(this.value && (Boolean) other.getValue());
    }

    @Override
    public Literal dividedby(Literal other) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SymbolBoolean does not support the dividecby operation.");
    }

    @Override
    public Literal modulo(Literal other) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SymbolBoolean does not support the modulo operation.");
    }

    @Override
    public Literal plus(Literal other) {
        return new SymbolBoolean(this.value || (Boolean) other.getValue());
    }

    @Override
    public Literal minus(Literal other) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SymbolBoolean does not support the minus operation.");
    }

    @Override
    public int compareTo(SymbolObject other) {
        if (other instanceof SymbolBoolean) {
            return Boolean.compare(value, (Boolean) other.getValue());
        }

        return -1;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }
}
