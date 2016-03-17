package com.kruth.symbol.literals;

import com.kruth.symbol.lexers.SpaceLexer;

import java.util.*;

/**
 * Created by kruthar on 2/24/16.
 */
public class SymbolNumber implements Literal {
    private static final Map<String, Integer> KEYWORDS;
    static {
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put("zero", 0);
        aMap.put("one", 1);
        aMap.put("two", 2);
        aMap.put("three", 3);
        aMap.put("four", 4);
        aMap.put("five", 5);
        aMap.put("six", 6);
        aMap.put("seven", 7);
        aMap.put("eight", 8);
        aMap.put("nine", 9);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    private int value;

    public SymbolNumber() {
        this(0);
    }

    public SymbolNumber(int num) {
        value = num;
    }

    public SymbolNumber(SpaceLexer lexer) {
        String string_num = "";
        while (lexer.hasNext() && KEYWORDS.containsKey(lexer.peek())) {
            string_num += KEYWORDS.get(lexer.next());
        }
        value = Integer.valueOf(string_num);
    }

    public String toString() {
        return String.valueOf(value);
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public Literal plus(Literal other) {
        if (other instanceof SymbolString) {
            return new SymbolString(this.toString() + other.toString());
        } else if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value + ((SymbolNumber) other).getValue());
        }

        System.out.println("ERROR: Unknown type for Plus operation with SymbolNumber: " + other.getClass());
        return new SymbolNumber(-1);
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }
}
