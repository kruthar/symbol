package com.kruth.symbol.literals;

import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.InvalidSymbolNumberException;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.lexers.SpaceLexer;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by kruthar on 2/24/16.
 */
public class SymbolNumber extends Literal {
    private static final Map<String, BigDecimal> KEYWORDS;

    private static Map<String, BigDecimal> singleMap;
    private static Map<String, BigDecimal> teenMap;
    private static Map<String, BigDecimal> tensMap;
    private static Map<String, BigDecimal> prefixMap;

    private static String dec = "dec";
    private static String hundred = "hundred";

    static {
        singleMap = new HashMap<>();
        singleMap.put("zero", BigDecimal.valueOf(0));
        singleMap.put("one", BigDecimal.valueOf(1));
        singleMap.put("two", BigDecimal.valueOf(2));
        singleMap.put("three", BigDecimal.valueOf(3));
        singleMap.put("four", BigDecimal.valueOf(4));
        singleMap.put("five", BigDecimal.valueOf(5));
        singleMap.put("six", BigDecimal.valueOf(6));
        singleMap.put("seven", BigDecimal.valueOf(7));
        singleMap.put("eight", BigDecimal.valueOf(8));
        singleMap.put("nine", BigDecimal.valueOf(9));

        teenMap = new HashMap<>();
        teenMap.put("ten", BigDecimal.valueOf(10));
        teenMap.put("eleven", BigDecimal.valueOf(11));
        teenMap.put("twelve", BigDecimal.valueOf(12));
        teenMap.put("thirteen", BigDecimal.valueOf(13));
        teenMap.put("fourteen", BigDecimal.valueOf(14));
        teenMap.put("fifteen", BigDecimal.valueOf(15));
        teenMap.put("sixteen", BigDecimal.valueOf(16));
        teenMap.put("seventeen", BigDecimal.valueOf(17));
        teenMap.put("eighteen", BigDecimal.valueOf(18));
        teenMap.put("nineteen", BigDecimal.valueOf(19));

        tensMap = new HashMap<>();
        tensMap.put("twenty", BigDecimal.valueOf(20));
        tensMap.put("thirty", BigDecimal.valueOf(30));
        tensMap.put("forty", BigDecimal.valueOf(40));
        tensMap.put("fifty", BigDecimal.valueOf(50));
        tensMap.put("sixty", BigDecimal.valueOf(60));
        tensMap.put("seventy", BigDecimal.valueOf(70));
        tensMap.put("eighty", BigDecimal.valueOf(80));
        tensMap.put("ninety", BigDecimal.valueOf(90));

        prefixMap = new HashMap<>();
        prefixMap.put("thousand", BigDecimal.valueOf(1000));
        prefixMap.put("million", BigDecimal.valueOf(1000000));
        prefixMap.put("billion", BigDecimal.valueOf(1000000000));

        Map<String, BigDecimal> unionMap = new HashMap<>();
        unionMap.putAll(singleMap);
        unionMap.putAll(teenMap);
        unionMap.putAll(tensMap);
        unionMap.putAll(prefixMap);
        unionMap.put(hundred, BigDecimal.valueOf(100));
        unionMap.put(dec, null);

        KEYWORDS = Collections.unmodifiableMap(unionMap);
    }

    private BigDecimal value;

    public SymbolNumber(int num) {
        value = BigDecimal.valueOf(num);
    }

    public SymbolNumber(Double num) {
        value = BigDecimal.valueOf(num);
    }

    public SymbolNumber(BigDecimal num) {
        value = num;
    }

    public SymbolNumber(SpaceLexer lexer) throws SymbolException {
        BigDecimal left = parse(lexer);
        BigDecimal right = BigDecimal.valueOf(0);

        if (lexer.hasNext() && lexer.peek().equals(dec)) {
            lexer.next();
            right = parse(lexer);
        }

        value = left.add(right.divide(BigDecimal.valueOf(Math.pow(10, right.toString().length()))));
    }

     public BigDecimal parse(SpaceLexer lexer) throws SymbolException{
        String string_num = "";
        BigDecimal total = BigDecimal.valueOf(0);
        Boolean digits = false;

        if (singleMap.containsKey(lexer.peek()) && singleMap.containsKey(lexer.peek(1))) {
            digits = true;
        }

        while (lexer.hasNext() && KEYWORDS.containsKey(lexer.peek().toLowerCase())) {
            if (lexer.peek().equals(dec)) {
                break;
            }

            String next = lexer.next();

            if (digits) {
                string_num += singleMap.get(next);
            } else {
                if (singleMap.containsKey(next)) {
                    if (string_num.length() == 0) {
                        string_num = String.valueOf(singleMap.get(next));
                    } else {
                        string_num = String.valueOf(new BigDecimal(string_num).add(singleMap.get(next)));
                    }
                } else if (teenMap.containsKey(next)) {
                    if (string_num.length() == 0) {
                        string_num = String.valueOf(teenMap.get(next));
                    } else {
                        string_num = String.valueOf(new BigDecimal(string_num).add(teenMap.get(next)));
                    }
                } else if (tensMap.containsKey(next)) {
                    if (!string_num.equals("")) {
                        string_num = String.valueOf(new BigDecimal(string_num).add(tensMap.get(next)));
                    } else {
                        if (singleMap.containsKey(lexer.peek())) {
                            BigDecimal sectionValue = BigDecimal.valueOf(0);
                            if (!string_num.equals("")) {
                                sectionValue = sectionValue.add(new BigDecimal(string_num));
                            }
                            sectionValue = sectionValue.add(tensMap.get(next).add(singleMap.get(lexer.next())));
                            string_num = String.valueOf(sectionValue);
                        } else if (tensMap.containsKey(lexer.peek())) {
                            throw new InvalidSymbolNumberException("Two tens digits in a row is not valid");
                        } else if (lexer.peek() == null || lexer.peek().equals("sep") || lexer.peek().equals(hundred) || prefixMap.containsKey(lexer.peek())) {
                            string_num = String.valueOf(tensMap.get(next));
                        }
                    }
                } else if (next.equals(hundred)) {
                    if (string_num.length() <= 2) {
                        string_num = String.valueOf(Integer.valueOf(string_num) * 100);
                    }
                } else if (prefixMap.containsKey(next)) {
                    if (string_num.equals("")) {
                        throw new InvalidSymbolNumberException("Unspecified number of" + next + "s");
                    }

                    total = total.add(new BigDecimal(string_num).multiply(prefixMap.get(next)));
                    string_num = "";
                }
            }
        }

        if (!string_num.equals("")) {
            total = total.add(new BigDecimal(string_num));
        }

        return total;
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
            return new SymbolNumber(this.value.multiply((BigDecimal) other.getValue()));
        }

        throw new OperationNotSupportedException("Times not supported between SymbolNumber and " + other.getClass());
    }

    @Override
    public Literal dividedby(Literal other) throws OperationNotSupportedException {
        if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value.divide((BigDecimal) other.getValue()));
        }

        throw new OperationNotSupportedException("DivideBy not supported between SymbolNumber and " + other.getClass());
    }

    @Override
    public Literal modulo(Literal other) throws OperationNotSupportedException {
        if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value.remainder((BigDecimal) other.getValue()));
        }

        throw new OperationNotSupportedException("Modulo not supported between SymbolNumber and " + other.getClass());
    }

    @Override
    public Literal plus(Literal other) {
        if (other instanceof SymbolString) {
            return new SymbolString(this.toString() + other.toString());
        } else if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value.add((BigDecimal) other.getValue()));
        }

        System.out.println("ERROR: Unknown type for Plus operation with SymbolNumber: " + other.getClass());
        return new SymbolNumber(-1);
    }

    @Override
    public Literal minus(Literal other) throws OperationNotSupportedException {
        if (other instanceof SymbolNumber) {
            return new SymbolNumber(this.value.subtract((BigDecimal) other.getValue()));
        }

        throw new OperationNotSupportedException("Minus not supported between SymbolNumber and " + other.getClass());
    }

    public int compareTo(SymbolObject other) {
        if (other instanceof SymbolNumber) {
            return value.compareTo((BigDecimal) other.getValue());
        }

        return -1;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }
}
