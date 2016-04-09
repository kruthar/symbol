package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.expression.ExpressionComponent;

/**
 * Created by kruthar on 4/1/16.
 */
public class Variable implements ExpressionComponent {
    private String name;
    private InstructionState instructionState;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public static void parse(InstructionState instructionState, String line, Boolean execute) throws VariableDoesNotExistsException {
        String[] variableSplit = line.split(" ", 3);
        if (variableSplit[1].equals("is")) {
            if (execute) {
                instructionState.setVariable(variableSplit[0], new Expression(instructionState, variableSplit[2]).evaluate());
            }
        } else {
            System.out.println("Error in variable assignment: 'variable " + line + "'");
        }
    }
}
