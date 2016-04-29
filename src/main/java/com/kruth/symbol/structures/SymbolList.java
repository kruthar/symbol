package com.kruth.symbol.structures;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.LanguageObject;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.SymbolListIndexOutOfBoundsException;
import com.kruth.symbol.exceptions.UnexpectedKeywordException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.SymbolNumber;

import java.math.BigDecimal;
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

    public SymbolList(InstructionState instructionState, SpaceLexer lexer) throws SymbolException {
        value = new ArrayList<>();

        // Lex out the starting 'list' keyword
        lexer.next();

        if (!lexer.peek().equals("open")) {
            throw new UnexpectedKeywordException("Expecting 'open' keyword to start SymbolList, found '" + lexer.next() + "'.");
        } else {
            // lex out the 'open' keyword
            lexer.next();
        }

        try {
            while (!lexer.peek().toLowerCase().equals("close")) {
                value.add(new Expression(instructionState, lexer).evaluate());
            }
        } catch (NullPointerException e) {
            throw new UnexpectedKeywordException("Reached end of Expression, but expecting the 'close' keyword to end SymbolList.", e);
        }

        // Lex out the ending 'close' keyword
        lexer.next();
    }

    @Override
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

    @Override
    public LanguageObject getLanguageObject() {
        return new LanguageList(this);
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
}
