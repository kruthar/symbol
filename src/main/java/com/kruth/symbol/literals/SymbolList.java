package com.kruth.symbol.literals;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;

import javax.naming.OperationNotSupportedException;
import java.util.*;

/**
 * Created by kruthar on 4/3/16.
 */
public class SymbolList extends Literal {
    private static final Map<String, Boolean> KEYWORDS;
    static {
        Map<String, Boolean> aMap = new HashMap<>();
        aMap.put("list", false);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    private List<Literal> value;

    public SymbolList(List<Literal> list) {
        value = new ArrayList<>(list);
    }

    public SymbolList(InstructionState instructionState, SpaceLexer lexer) {
        value = new ArrayList<>();
        // Lex out the starting 'list' keyword
        lexer.next();
        while (!lexer.peek().toLowerCase().equals("list")) {
            value.add(new Expression(instructionState, lexer).evaluate());
        }
        // Lex out the ending 'list' keyword
        lexer.next();
    }

    public String toString() {
        String result = "[";
        for (int i = 0; i < value.size(); i++) {
            result += value.get(i).toString();
            if (i < value.size() - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }

    public List<Literal> getValue() {
        return value;
    }

    public int size() {
        return value.size();
    }

    public List<Literal> getList() {
        return value;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }

    @Override
    public Literal times(Literal other) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SymbolList does not support the times operation.");
    }

    @Override
    public Literal dividedby(Literal other) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SymbolList does not support the dividecby operation.");
    }

    @Override
    public Literal modulo(Literal other) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SymbolList does not support the modulo operation.");
    }

    @Override
    public Literal plus(Literal other) {
        List<Literal> result = new ArrayList<>(value);
        result.addAll(((SymbolList) other).getList());
        return new SymbolList(result);
    }

    @Override
    public Literal minus(Literal other) throws OperationNotSupportedException {
        throw new OperationNotSupportedException("SymbolList does not support the minus operation.");
    }

    @Override
    public int comparedTo(Literal other) throws OperationNotSupportedException {
        if (value.size() != ((SymbolList) other).size())  {
            return -1;
        }

        for (int i = 0; i < value.size(); i++) {
            if (((SymbolBoolean) value.get(i).notEqualTo(other)).getValue()) {
                return 1;
            }
        }

        return 0;
    }
}
