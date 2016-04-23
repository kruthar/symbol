package com.kruth.symbol.instructions;

import com.kruth.symbol.ErrorState;
import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.UnexpectedKeywordException;
import com.kruth.symbol.exceptions.UnexpectedStateException;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.literals.Literal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by kruthar on 4/1/16.
 */
public class Function {
    private String name;
    private String[] parameters;
    private List<String> instructions;
    private int startLine;

    public Function(String name, String[] parameters, List<String> instructions, int startLine) {
        this.name = name;
        this.parameters = parameters;
        this.instructions = instructions;
        this.startLine = startLine;
    }

    public static void parse(InstructionState instructionState, String line) throws SymbolException{
        int startLine = ErrorState.getLine();
        String[] lineSplit = line.split(" ");

        if (lineSplit.length < 1) {
            throw new UnexpectedStateException("Function definition requires a name.");
        }

        String name = lineSplit[0];
        String[] parameters = new String[0];

        // If the function signature is longer then 'function name'
        if (lineSplit.length > 1) {
            // Then the function must be atleast 'function name accepts param1'
            if (lineSplit.length < 3) {
                if (lineSplit[1].equals("accepts")) {
                    throw new UnexpectedKeywordException("Function definition is missing parameter list.");
                }
                throw new UnexpectedKeywordException("Expecting the keyword 'accepts' followed by parameter list, found: " + lineSplit[1]);
            }

            if (!lineSplit[1].equals("accepts")) {
                throw new UnexpectedKeywordException("Expecting the keyword 'accepts', found: " + lineSplit[1]);
            }

            parameters = Arrays.copyOfRange(lineSplit, 2, lineSplit.length);
        }

        List<String> instructions = new ArrayList<>();

        String nextInstruction = instructionState.nextLine();
        String[] instructionSplit = nextInstruction.trim().split(" ");

        while (!instructionSplit[0].equals("end")) {
            instructions.add(nextInstruction);
            nextInstruction = instructionState.nextLine();
            instructionSplit = nextInstruction.trim().split(" ");
        }

        // Lex out the "end" line
        instructionState.addFunction(new Function(name, parameters, instructions, startLine));
    }

    public String[] getParameters() {
        return parameters;
    }

    public String getKey() {
        return name;
    }

    public SymbolObject execute(InstructionState instructionState, Map<String, Expression> parameterExpressionMap) throws SymbolException {
        InstructionState functionState = new InstructionState();
        functionState.setLineLexerList(instructions);
        functionState.setFunctionMap(instructionState.getFunctionMap());

        int previousLine = ErrorState.getLine();
        ErrorState.setLine(startLine);

        for (Map.Entry<String, Expression> parameter: parameterExpressionMap.entrySet()) {
            functionState.setVariable(parameter.getKey(), parameter.getValue().evaluate());
        }

        // Until we have a better understand about how we want to handle 'global' variables and functions,
        // functions will have a self contained state and so will not need to associate a parent state.
        //functionState.setParentState(instructionState);

        while (functionState.hasNextLine()) {
            functionState.routeNextInstruction(true);
        }

        ErrorState.setLine(previousLine);
        return functionState.getReturnValue();
    }
}
