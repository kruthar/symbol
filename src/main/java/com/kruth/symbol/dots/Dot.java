package com.kruth.symbol.dots;

import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.expression.ExpressionComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kruthar on 4/5/16.
 */
public class Dot implements ExpressionComponent {
    private String name;
    private List<SymbolObject> parameters;

    public Dot(String name, List<SymbolObject> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public List<SymbolObject> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        String result = "name(";

        for (int i = 0; i < parameters.size(); i++) {
            result += parameters.get(i);

            if (i < parameters.size() - 1) {
                result += ", ";
            }
        }

        result += ")";
        return result;
    }
}
