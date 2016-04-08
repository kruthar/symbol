package com.kruth.symbol.literals;

import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.lexers.SpaceLexer;

import javax.naming.OperationNotSupportedException;
import java.util.*;

/**
 * Created by kruthar on 2/24/16.
 */
public class SymbolNumber extends Literal {
    private static final Map<String, Integer> KEYWORDS;

    private static Map<String, Integer> singleMap;
    private static Map<String, Integer> teenMap;
    private static Map<String, Integer> tensMap;
    private static Map<String, Integer> prefixMap;

    static {
        singleMap = new HashMap<>();
        singleMap.put("zero", 0);
        singleMap.put("one", 1);
        singleMap.put("two", 2);
        singleMap.put("three", 3);
        singleMap.put("four", 4);
        singleMap.put("five", 5);
        singleMap.put("six", 6);
        singleMap.put("seven", 7);
        singleMap.put("eight", 8);
        singleMap.put("nine", 9);

        teenMap = new HashMap<>();
        teenMap.put("ten", 10);
        teenMap.put("eleven", 11);
        teenMap.put("twelve", 12);
        teenMap.put("thirteen", 13);
        teenMap.put("fourteen", 14);
        teenMap.put("fifteen", 15);
        teenMap.put("sixteen", 16);
        teenMap.put("seventeen", 17);
        teenMap.put("eighteen", 18);
        teenMap.put("nineteen", 19);

        tensMap = new HashMap<>();
        tensMap.put("twenty", 20);
        tensMap.put("thirty", 30);
        tensMap.put("forty", 40);
        tensMap.put("fifty", 50);
        tensMap.put("sixty", 60);
        tensMap.put("seventy", 70);
        tensMap.put("eighty", 80);
        tensMap.put("ninety", 90);

        prefixMap = new HashMap<>();
        prefixMap.put("thousand", 1000);

        Map<String, Integer> unionMap = new HashMap<>();
        unionMap.putAll(singleMap);
        unionMap.putAll(teenMap);
        unionMap.putAll(tensMap);
        unionMap.putAll(prefixMap);
        unionMap.put("hundred", 100);

        KEYWORDS = Collections.unmodifiableMap(unionMap);
    }

    private int value;

    public SymbolNumber(int num) {
        value = num;
    }

    public SymbolNumber(SpaceLexer lexer) {
        String string_num = "";
        int total = 0;
        Boolean digits = false;

        if (singleMap.containsKey(lexer.peek()) && singleMap.containsKey(lexer.peek(1))) {
            digits = true;
        }

        while (lexer.hasNext() && KEYWORDS.containsKey(lexer.peek().toLowerCase())) {
            String next = lexer.next();

            if (digits) {
                string_num += singleMap.get(next);
            } else {
                if (singleMap.containsKey(next)) {
                    if (string_num.length() == 0) {
                        string_num = String.valueOf(singleMap.get(next));
                    } else {
                        string_num = String.valueOf(Integer.valueOf(string_num) + singleMap.get(next));
                    }
                } else if (teenMap.containsKey(next)) {
                    if (string_num.length() == 0) {
                        string_num = String.valueOf(teenMap.get(next));
                    } else {
                        string_num = String.valueOf(Integer.valueOf(string_num) + teenMap.get(next));
                    }
                } else if (tensMap.containsKey(next)) {
                    if (!string_num.equals("")) {
                        string_num = String.valueOf(Integer.valueOf(string_num) + tensMap.get(next));
                    } else {
                        if (singleMap.containsKey(lexer.peek())) {
                            int sectionValue = 0;
                            if (!string_num.equals("")) {
                                sectionValue += Integer.valueOf(string_num);
                            }
                            sectionValue += tensMap.get(next) + singleMap.get(lexer.next());
                            string_num = String.valueOf(sectionValue);
                        } else if (tensMap.containsKey(lexer.peek())) {
                            System.out.println("ERROR: Invalid SymbolNumber, two tens digits in a row is not allowed");
                        } else if (lexer.peek() == null || lexer.peek().equals("sep") || lexer.peek().equals("hundred") || prefixMap.containsKey(lexer.peek())) {
                            string_num = String.valueOf(tensMap.get(next));
                        }
                    }
                } else if (next.equals("hundred")) {
                    if (string_num.length() <= 2) {
                        string_num = String.valueOf(Integer.valueOf(string_num) * 100);
                    }
                } else if (prefixMap.containsKey(next)) {
                    if (string_num.equals("")) {
                        System.out.println("ERROR: Invalid SymbolNumber, specify how many " + next + "s");
                    }

                    total += Integer.valueOf(string_num) * prefixMap.get(next);
                    string_num = "";
                }
            }
        }

        if (!string_num.equals("")) {
            total += Integer.valueOf(string_num);
        }
        value = total;
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

    public int compareTo(SymbolObject other) {
        if (other instanceof SymbolNumber) {
            return Integer.compare(value, (Integer) other.getValue());
        }

        return -1;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }
}
