package com.kruth.symbol.structures;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.Literal;

import javax.naming.OperationNotSupportedException;
import java.util.*;

/**
 * Created by kruthar on 4/3/16.
 */
public class SymbolList extends Structure {
    private static final Map<String, Boolean> KEYWORDS;
    static {
        Map<String, Boolean> aMap = new HashMap<>();
        aMap.put("list", false);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    private List<SymbolObject> value;

    public SymbolList(List<SymbolObject> list) {
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

    @Override
    public Object getValue() {
        return value;
    }

    public int size() {
        return value.size();
    }

    public List<SymbolObject> getList() {
        return value;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }

    @Override
    public SymbolObject plus(SymbolObject other) {
        List<SymbolObject> result = new ArrayList<>(value);
        result.addAll(((SymbolList) other).getList());
        return new SymbolList(result);
    }

    @Override
    public int compareTo(SymbolObject other) {
        if (!(other instanceof SymbolList)) {
            return -1;
        }

        SymbolList sl = (SymbolList) other;

        if (value.equals(sl)) {
            return 0;
        }

        return -1;
    }
}
