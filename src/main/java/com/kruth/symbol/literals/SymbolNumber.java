package com.kruth.symbol.literals;

import com.kruth.symbol.lexers.SpaceLexer;

import javax.naming.OperationNotSupportedException;
import java.util.*;

/**
 * Created by kruthar on 2/24/16.
 */
public class SymbolNumber extends Literal {
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

    public SymbolNumber(int num) {
        value = num;
    }

    public SymbolNumber(SpaceLexer lexer) {
        String string_num = "";
        while (lexer.hasNext() && KEYWORDS.containsKey(lexer.peek().toLowerCase())) {
            string_num += KEYWORDS.get(lexer.next());
        }
        value = Integer.valueOf(string_num);
    }

    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Literal times(Literal other) throws OperationNotSupportedException {
        if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value * (Integer) other.getValue());
        }

        throw new OperationNotSupportedException("Times not supported between SymbolNumber and " + other.getClass());
    }

    @Override
    public Literal dividedby(Literal other) throws OperationNotSupportedException {
        if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value / (Integer) other.getValue());
        }

        throw new OperationNotSupportedException("DivideBy not supported between SymbolNumber and " + other.getClass());
    }

    @Override
    public Literal modulo(Literal other) throws OperationNotSupportedException {
        if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value % (Integer) other.getValue());
        }

        throw new OperationNotSupportedException("Modulo not supported between SymbolNumber and " + other.getClass());
    }

    @Override
    public Literal plus(Literal other) {
        if (other instanceof SymbolString) {
            return new SymbolString(this.toString() + other.toString());
        } else if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value + (Integer) other.getValue());
        }

        System.out.println("ERROR: Unknown type for Plus operation with SymbolNumber: " + other.getClass());
        return new SymbolNumber(-1);
    }

    @Override
    public Literal minus(Literal other) throws OperationNotSupportedException {
        if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value - (Integer) other.getValue());
        }

        throw new OperationNotSupportedException("Minus not supported between SymbolNumber and " + other.getClass());
    }

    public int compareTo(Literal other) {
        if (other instanceof SymbolNumber) {
            return Integer.compare(value, (Integer) other.getValue());
        }

        return -1;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }
}
