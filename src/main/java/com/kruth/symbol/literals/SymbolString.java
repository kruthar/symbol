package com.kruth.symbol.literals;

import com.kruth.symbol.lexers.SpaceLexer;

import javax.naming.OperationNotSupportedException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kruthar on 2/24/16.
 */
public class SymbolString extends Literal {
    private static final Map<String, Integer> KEYWORDS;
    static {
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put("string", 0);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    private String value;

    public SymbolString(String s) {
        value = s;
    }

    public SymbolString(SpaceLexer lexer) {
        if (!lexer.hasNext() || lexer.next().toLowerCase() != "string") {
            // TODO: Do something bad here
        }

        if (!lexer.hasNext()) {
            value = "";
        } else {
            String result = lexer.next();

            while (lexer.hasNext() && !lexer.peek().toLowerCase().equals("string")) {
                result += " " + lexer.next();
            }
            lexer.next();

            value = result;
        }
    }

    public void append(String a) {
        value += a;
    }

    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Literal times(Literal other) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SymbolString does not support the times operation.");
    }

    @Override
    public Literal dividedby(Literal other) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SymbolString does not support the dividedby operation.");
    }

    @Override
    public Literal modulo(Literal other) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SymbolString does not support the modulo operation.");
    }

    @Override
    public Literal plus(Literal other) {
        return new SymbolString(this.value + other.toString());
    }

    @Override
    public Literal minus(Literal other) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SymbolString does not support the minus operation.");
    }

    @Override
    public int comparedTo(Literal other) throws OperationNotSupportedException {
        if (other instanceof SymbolString) {
            return this.value.compareTo(((SymbolString) other).getValue());
        }

        throw new OperationNotSupportedException("ComparedTo not supported between SymbolString and " + other.getClass());
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }

}
