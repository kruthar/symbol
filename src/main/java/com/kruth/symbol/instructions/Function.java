package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.expression.Expression;

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

    public Function(String name, String[] parameters, List<String> instructions) {
        this.name = name;
        this.parameters = parameters;
        this.instructions = instructions;
    }

    public static void parse(InstructionState instructionState, String line) {
        String[] lineSplit = line.split(" ");

        if (lineSplit.length < 1) {
            System.out.println("Functions require a name");
            System.exit(1);
        }

        String name = lineSplit[0];
        String[] parameters = new String[0];

        // If the function signature is longer then 'function name'
        if (lineSplit.length > 1) {
            // Then the function must be atleast 'function name accepts param1'
            if (lineSplit.length < 3) {
                System.out.println("No parameters specified for function: " + name);
                System.exit(1);
            }

            if (!lineSplit[1].equals("accepts")) {
                System.out.println("Expecting keyword 'accepts' found: " + lineSplit[1]);
                System.exit(1);
            }

            parameters = Arrays.copyOfRange(lineSplit, 2, lineSplit.length);
        }

        List<String> instructions = new ArrayList<String>();

        String nextInstruction = instructionState.nextLine();
        String[] instructionSplit = nextInstruction.trim().split(" ");

        while (!instructionSplit[0].equals("end")) {
            instructions.add(nextInstruction);
            nextInstruction = instructionState.nextLine();
            instructionSplit = nextInstruction.trim().split(" ");
        }

        // Lex out the "end" line
        instructionState.addFunction(new Function(name, parameters, instructions));
    }

    public String getKey() {
        return name + parameters.length;
    }
}
