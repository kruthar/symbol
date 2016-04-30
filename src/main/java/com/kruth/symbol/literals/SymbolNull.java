package com.kruth.symbol.literals;

import com.kruth.symbol.LanguageObject;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.UnexpectedKeywordException;
import com.kruth.symbol.lexers.SpaceLexer;

import javax.naming.OperationNotSupportedException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kruthar on 4/3/16.
 */
public class SymbolNull extends Literal {
    private static final Map<String, Boolean> KEYWORDS;
    static {
        Map<String, Boolean> aMap = new HashMap<>();
        aMap.put("null", null);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    public SymbolNull(SpaceLexer lexer) throws SymbolException {
        if (!lexer.peek().equals("null")) {
            throw new UnexpectedKeywordException("Expecting keyword 'null', found " + lexer.peek());
        }

        lexer.next();
    }

    @Override
    public String toString() {
        return "null";
    }

    @Override
    public Literal times(Literal other) throws OperationNotSupportedException {
        return null;
    }

    @Override
    public Literal dividedby(Literal other) throws OperationNotSupportedException {
        return null;
    }

    @Override
    public Literal modulo(Literal other) throws OperationNotSupportedException {
        return null;
    }

    @Override
    public Literal plus(Literal other) {
        return null;
    }

    @Override
    public Literal minus(Literal other) throws OperationNotSupportedException {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public LanguageObject getLanguageObject() {
        return new LanguageNull();
    }

    @Override
    public int compareTo(SymbolObject other) {
        if (other instanceof SymbolNull) {
            return 0;
        }
        return -1;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }
}
