package com.kruth.symbol.structures;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.Literal;
import com.kruth.symbol.literals.SymbolNumber;

import javax.naming.OperationNotSupportedException;
import java.math.BigInteger;
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

    public SymbolList(InstructionState instructionState, SpaceLexer lexer) throws VariableDoesNotExistsException {
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

    public List<SymbolObject> getList() {
        return value;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
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

    @Override
    public Iterator<SymbolObject> getIterator() {
        return value.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof SymbolList)) {
            return false;
        }

        for (int i = 0; i < this.getList().size(); i++) {
            if (!this.value.get(i).equals(((SymbolList) o).getList().get(i))) {
                return false;
            }
        }

        return true;
    }

    public void put(SymbolObject obj) {
        value.add(obj);
    }

    public void put(SymbolNumber index, SymbolObject obj) {
        value.add(((BigInteger) index.getValue()).intValue(), obj);
    }

    public SymbolObject get(SymbolNumber index) {
        return value.get(((BigInteger) index.getValue()).intValue());
    }

    public SymbolNumber size() {
        return new SymbolNumber(value.size());
    }

    public void remove(SymbolNumber index) {
        value.remove(((BigInteger) index.getValue()).intValue());
    }
}
