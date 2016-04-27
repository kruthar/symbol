package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.dots.Dot;
import com.kruth.symbol.exceptions.*;
import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.expression.ExpressionComponent;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.SymbolNull;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kruthar on 4/23/16.
 */
public class Module implements ExpressionComponent {
    private String name;
    private InstructionState state;

    public Module(String name, String path) throws SymbolException {
        this.name = name;
        this.state = new InstructionState();

        File moduleFile;
        URL resourceURL = getClass().getClassLoader().getResource("modules/" + path);

        // If file isn't in the resources folder then look at the absolute path.
        if (resourceURL != null) {
            moduleFile = new File(resourceURL.getFile());
        } else {
            moduleFile = new File(path);
        }

        BufferedReader reader;
        String line;
        List<String> lines = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(moduleFile));
        } catch (FileNotFoundException e) {
            throw new FailedToParseModuleException("Module file not found: " + path, e);
        }

        try {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new FailedToParseModuleException("Failed to parse module.", e);
        }

        state.setLineLexerList(lines);

        while (state.hasNextLine()) {
            String instruction = state.nextLine();

            if (instruction.isEmpty()) {
                continue;
            }

            String[] instructionSplit = instruction.split(" ", 2);

            if (instructionSplit[0].equals("function")) {
                Function.parse(state, instructionSplit[1]);
            } else if (instructionSplit[0].equals("variable")) {
                Variable.parse(state, instructionSplit[1], true);
            }
        }
    }

    public static void parse(InstructionState parentState, String line) throws SymbolException {
        SpaceLexer lexer = new SpaceLexer(line);

        if (lexer.hasNext()) {
            String filepath = lexer.next();

            if (lexer.hasNext()) {
                if (lexer.peek().equals("as")) {
                    lexer.next();

                    if (lexer.hasNext()) {
                        String alias = lexer.next();

                        if (lexer.hasNext()) {
                            throw new UnexpectedKeywordException("Expecting end of instruction, found: " + lexer.restOfLine());
                        }

                        parentState.addModule(new Module(alias, filepath));
                    } else {
                        throw new UnexpectedStateException("Expecting module alias, found end of instruction.");
                    }
                } else {
                    throw new UnexpectedKeywordException("Expecting the keyword 'as', found: " + lexer.peek());
                }
            } else {
                throw new UnexpectedKeywordException("Expecting the keyword 'as', found end of instruction.");
            }
        } else {
            throw new UnexpectedStateException("Expecting module path, found end of instruction.");
        }
    }

    public String getName() {
        return name;
    }

    public InstructionState getState() {
        return state;
    }

    public SymbolObject invoke(Dot dot) throws SymbolException{
        if (dot.getParameters().size() == 0 && state.hasVariable(dot.getName())) {
            return state.getVariable(dot.getName());
        } else if (state.hasFunction(dot.getName())) {
            Function func = state.getFunctionMap().get(dot.getName());
            return func.execute(state, getFunctionParameterMap(func.getParameters(), dot.getParameters()));
        } else {
            throw new FunctionNotDefinedException("Variable or Function not defined for: " + dot.getName());
        }
    }

    public Map<String, Expression> getFunctionParameterMap(String[] keys, List<SymbolObject> values) {
        Map<String, Expression> result = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            result.put(keys[i], new Expression(values.get(i)));
        }
        return result;
    }
}
