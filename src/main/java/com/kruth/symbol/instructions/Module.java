package com.kruth.symbol.instructions;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.exceptions.FailedToParseModuleException;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.UnexpectedKeywordException;
import com.kruth.symbol.exceptions.UnexpectedStateException;
import com.kruth.symbol.lexers.LineLexer;
import com.kruth.symbol.lexers.SpaceLexer;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kruthar on 4/23/16.
 */
public class Module {
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
}
