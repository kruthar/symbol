package com.kruth.symbol.expression;

import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.Literal;
import com.kruth.symbol.literals.SymbolNumber;
import com.kruth.symbol.literals.SymbolString;
import com.kruth.symbol.operations.Operation;
import com.kruth.symbol.operations.OperationParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kruthar on 2/24/16.
 */
public class Expression {
    private List<ExpressionComponent> components;

    public Expression() {
        components = new ArrayList<>();
    }

    public Expression(ExpressionComponent component) {
        components = Arrays.asList(component);
    }

    public String toString() {
        String result = "";

        for (ExpressionComponent component: components) {
            result += component.toString();
        }

        return result;
    }

    public static Expression parse(String expression) {
        SpaceLexer lexer = new SpaceLexer(expression);
        Expression result = new Expression();

        while (lexer.hasNext()) {
            if (SymbolNumber.hasKeyword(lexer.peek())) {
                result.addComponent(new SymbolNumber(lexer));
            } else if (SymbolString.hasKeyword(lexer.peek())) {
                result.addComponent(new SymbolString(lexer));
            } else if (OperationParser.hasKeyword(lexer.peek())) {
                result.addComponent(OperationParser.parse(lexer));
            } else {
                System.out.println("Unrecognized Expression start");
                System.exit(1);
            }
        }

        return result;
    }

    public void addComponent(ExpressionComponent component) {
        if (!components.isEmpty() && (
            (components.get(components.size() - 1) instanceof Literal && component instanceof Literal) ||
            (components.get(components.size() -1) instanceof Operation && component instanceof Operation))) {
            System.out.println("bad expression - do something bad here");
            System.exit(1);
        }
        components.add(component);
    }
}
