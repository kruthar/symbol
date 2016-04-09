package com.kruth.symbol.dots;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.expression.ExpressionComponent;
import com.kruth.symbol.lexers.SpaceLexer;

import java.util.*;

/**
 * Created by kruthar on 4/5/16.
 */
public class DotParser {
    private static final Map<String, Integer> KEYWORDS;
    static {
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put("dot", 0);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }

    public static ExpressionComponent parse(InstructionState instructionState, SpaceLexer lexer) throws VariableDoesNotExistsException {
        // Lex out the "dot"
        lexer.next();

        String name = lexer.next();
        List<SymbolObject> parameters = new ArrayList<>();

        while (!lexer.peek().equals("sep")) {
            parameters.add(new Expression(instructionState, lexer).evaluate());
        }

        // Lex out the 'sep'
        lexer.next();

        return new Dot(name, parameters);
    }
}
