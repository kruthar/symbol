package com.kruth.symbol;

import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.Literal;
import com.kruth.symbol.literals.SymbolNumber;
import com.kruth.symbol.literals.SymbolString;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kruthar on 2/24/16.
 */
public class Expression {
    private List<Literal> literals;

    public Expression(Literal literal) {
        literals = Arrays.asList(literal);
    }

    public String toString() {
        String result = "";

        for (Literal literal: literals) {
            result += literal.toString();
        }

        return result;
    }

    public static Expression parse(String expression) {
        SpaceLexer lexer = new SpaceLexer(expression);

        if (SymbolNumber.hasKeyword(lexer.peek())) {
            return new Expression(new SymbolNumber(lexer));
        } else if (SymbolString.hasKeyword(lexer.peek())) {
            return new Expression(new SymbolString(lexer));
        }

        try {
            throw new Exception("Unknown lexer state at: " + lexer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
