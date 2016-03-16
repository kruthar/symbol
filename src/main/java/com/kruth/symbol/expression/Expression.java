package com.kruth.symbol.expression;

import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.Literal;
import com.kruth.symbol.literals.SymbolNumber;
import com.kruth.symbol.literals.SymbolString;
import com.kruth.symbol.operations.Operation;
import com.kruth.symbol.operations.OperationParser;

import java.util.*;

/**
 * Created by kruthar on 2/24/16.
 */
public class Expression implements ExpressionComponent {
    private static final Map<String, Integer> KEYWORDS;
    static {
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put("open", 0);
        aMap.put("close", 1);

        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    private List<ExpressionComponent> components;

    public Expression() {
        components = new ArrayList<>();
    }

    public Expression(ExpressionComponent component) {
        components = Arrays.asList(component);
    }


    public Expression(String expressionString) {
        this(new SpaceLexer(expressionString));
    }

    public Expression(SpaceLexer lexer) {
        components = new ArrayList<>();

        while (lexer.hasNext()) {
            if (Expression.hasKeyword(lexer.peek())) {
                String keyword = lexer.next();
                // If we are opening an expression then close the current expression,
                // then pass the lexer to in inner expression
                if (keyword.equals("open")) {
                    addComponent(new Expression(lexer));
                } else if (keyword.equals("close")) {
                    // If we are closing an expression, then end the loop here and allow the Expression to finish
                    // and the lexer to continue in the upper level of the stack
                    break;
                } else {
                    System.out.println("Unrecognized Expression keyword: " + keyword);
                    System.exit(1);
                }
            } else if (SymbolNumber.hasKeyword(lexer.peek())) {
                addComponent(new SymbolNumber(lexer));
            } else if (SymbolString.hasKeyword(lexer.peek())) {
                addComponent(new SymbolString(lexer));
            } else if (OperationParser.hasKeyword(lexer.peek())) {
                addComponent(OperationParser.parse(lexer));
            } else {
                System.out.println("Unrecognized Expression start");
                System.exit(1);
            }
        }
    }

    public void addComponent(ExpressionComponent component) {
        if (!components.isEmpty() && (
            (components.get(components.size() - 1) instanceof Literal && component instanceof Literal) ||
            (components.get(components.size() -1) instanceof Operation && component instanceof Operation))) {
            System.out.println("bad expression - two literals or two operations");
            System.exit(1);
        }
        components.add(component);
    }

    public String toString() {
        String result = "";

        for (ExpressionComponent component: components) {
            result += component.toString();
        }

        return result;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }
}
