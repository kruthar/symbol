package com.kruth.symbol.literals;

import com.kruth.symbol.lexers.SpaceLexer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kruthar on 2/24/16.
 */
public class SymbolString implements Literal {
    private static final Map<String, Integer> KEYWORDS;
    static {
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put("stringstart", 0);
        aMap.put("stringend", 1);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    private String value;

    public SymbolString() {
        this("");
    }

    public SymbolString(String s) {
        value = s;
    }

    public SymbolString(SpaceLexer lexer) {
        if (!lexer.hasNext() || lexer.next() != "stringstart") {
            // TODO: Do something bad here
        }

        if (!lexer.hasNext()) {
            value = "";
        } else {
            String result = lexer.next();

            while (lexer.hasNext() && !lexer.peek().equals("stringend")) {
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

    @Override
    public Literal plus(Literal other) {
        return new SymbolString(this.value + other.toString());
    }

    @Override
    public Literal minus(Literal other) {
        System.out.println("ERROR: SymbolString does not support minus operation");
        return new SymbolString("ERROR: SymbolString does not support minus operation");
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }

}
