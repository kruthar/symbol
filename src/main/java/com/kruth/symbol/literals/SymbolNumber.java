package com.kruth.symbol.literals;

import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.InvalidSymbolNumberException;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.lexers.SpaceLexer;

import javax.naming.OperationNotSupportedException;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by kruthar on 2/24/16.
 */
public class SymbolNumber extends Literal {
    private static final Map<String, BigInteger> KEYWORDS;

    private static Map<String, BigInteger> singleMap;
    private static Map<String, BigInteger> teenMap;
    private static Map<String, BigInteger> tensMap;
    private static Map<String, BigInteger> prefixMap;

    static {
        singleMap = new HashMap<>();
        singleMap.put("zero", BigInteger.valueOf(0));
        singleMap.put("one", BigInteger.valueOf(1));
        singleMap.put("two", BigInteger.valueOf(2));
        singleMap.put("three", BigInteger.valueOf(3));
        singleMap.put("four", BigInteger.valueOf(4));
        singleMap.put("five", BigInteger.valueOf(5));
        singleMap.put("six", BigInteger.valueOf(6));
        singleMap.put("seven", BigInteger.valueOf(7));
        singleMap.put("eight", BigInteger.valueOf(8));
        singleMap.put("nine", BigInteger.valueOf(9));

        teenMap = new HashMap<>();
        teenMap.put("ten", BigInteger.valueOf(10));
        teenMap.put("eleven", BigInteger.valueOf(11));
        teenMap.put("twelve", BigInteger.valueOf(12));
        teenMap.put("thirteen", BigInteger.valueOf(13));
        teenMap.put("fourteen", BigInteger.valueOf(14));
        teenMap.put("fifteen", BigInteger.valueOf(15));
        teenMap.put("sixteen", BigInteger.valueOf(16));
        teenMap.put("seventeen", BigInteger.valueOf(17));
        teenMap.put("eighteen", BigInteger.valueOf(18));
        teenMap.put("nineteen", BigInteger.valueOf(19));

        tensMap = new HashMap<>();
        tensMap.put("twenty", BigInteger.valueOf(20));
        tensMap.put("thirty", BigInteger.valueOf(30));
        tensMap.put("forty", BigInteger.valueOf(40));
        tensMap.put("fifty", BigInteger.valueOf(50));
        tensMap.put("sixty", BigInteger.valueOf(60));
        tensMap.put("seventy", BigInteger.valueOf(70));
        tensMap.put("eighty", BigInteger.valueOf(80));
        tensMap.put("ninety", BigInteger.valueOf(90));

        prefixMap = new HashMap<>();
        prefixMap.put("thousand", BigInteger.valueOf(1000));
        prefixMap.put("million", BigInteger.valueOf(1000000));
        prefixMap.put("billion", BigInteger.valueOf(1000000000));

        Map<String, BigInteger> unionMap = new HashMap<>();
        unionMap.putAll(singleMap);
        unionMap.putAll(teenMap);
        unionMap.putAll(tensMap);
        unionMap.putAll(prefixMap);
        unionMap.put("hundred", BigInteger.valueOf(100));

        KEYWORDS = Collections.unmodifiableMap(unionMap);
    }

    private BigInteger value;

    public SymbolNumber(int num) {
        value = BigInteger.valueOf(num);
    }

    public SymbolNumber(BigInteger num) {
        value = num;
    }

    public SymbolNumber(SpaceLexer lexer) throws SymbolException {
        String string_num = "";
        BigInteger total = BigInteger.valueOf(0);
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
                        string_num = String.valueOf(new BigInteger(string_num).add(singleMap.get(next)));
                    }
                } else if (teenMap.containsKey(next)) {
                    if (string_num.length() == 0) {
                        string_num = String.valueOf(teenMap.get(next));
                    } else {
                        string_num = String.valueOf(new BigInteger(string_num).add(teenMap.get(next)));
                    }
                } else if (tensMap.containsKey(next)) {
                    if (!string_num.equals("")) {
                        string_num = String.valueOf(new BigInteger(string_num).add(tensMap.get(next)));
                    } else {
                        if (singleMap.containsKey(lexer.peek())) {
                            BigInteger sectionValue = BigInteger.valueOf(0);
                            if (!string_num.equals("")) {
                                sectionValue = sectionValue.add(new BigInteger(string_num));
                            }
                            sectionValue = sectionValue.add(tensMap.get(next).add(singleMap.get(lexer.next())));
                            string_num = String.valueOf(sectionValue);
                        } else if (tensMap.containsKey(lexer.peek())) {
                            throw new InvalidSymbolNumberException("Two tens digits in a row is not valid");
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
                        throw new InvalidSymbolNumberException("Unspecified number of" + next + "s");
                    }

                    total = total.add(new BigInteger(string_num).multiply(prefixMap.get(next)));
                    string_num = "";
                }
            }
        }

        if (!string_num.equals("")) {
            total = total.add(new BigInteger(string_num));
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
            return new SymbolNumber(this.value.multiply((BigInteger) other.getValue()));
        }

        throw new OperationNotSupportedException("Times not supported between SymbolNumber and " + other.getClass());
    }

    @Override
    public Literal dividedby(Literal other) throws OperationNotSupportedException {
        if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value.divide((BigInteger) other.getValue()));
        }

        throw new OperationNotSupportedException("DivideBy not supported between SymbolNumber and " + other.getClass());
    }

    @Override
    public Literal modulo(Literal other) throws OperationNotSupportedException {
        if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value.mod((BigInteger) other.getValue()));
        }

        throw new OperationNotSupportedException("Modulo not supported between SymbolNumber and " + other.getClass());
    }

    @Override
    public Literal plus(Literal other) {
        if (other instanceof SymbolString) {
            return new SymbolString(this.toString() + other.toString());
        } else if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value.add((BigInteger) other.getValue()));
        }

        System.out.println("ERROR: Unknown type for Plus operation with SymbolNumber: " + other.getClass());
        return new SymbolNumber(-1);
    }

    @Override
    public Literal minus(Literal other) throws OperationNotSupportedException {
        if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value.subtract((BigInteger) other.getValue()));
        }

        throw new OperationNotSupportedException("Minus not supported between SymbolNumber and " + other.getClass());
    }

    public int compareTo(SymbolObject other) {
        if (other instanceof SymbolNumber) {
            return value.compareTo((BigInteger) other.getValue());
        }

        return -1;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }
}
