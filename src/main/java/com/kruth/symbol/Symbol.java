package com.kruth.symbol;

import com.kruth.symbol.lexers.LineLexer;

/**
 * Created by kruthar on 2/23/16.
 */
public class Symbol {
    public static void main(String args[]) {
        Symbol.executeFile("test.symbol");
        //Symbol.execute("print stringstart Hello World! stringend");
    }

    public static void executeFile(String filename) {
        LineLexer lex = new LineLexer(filename);

        while (lex.hasNext()) {
            InstructionRouter.getInstruction(lex.next()).runInstruction();
        }
    }

    public static void execute(String line) {
        InstructionRouter.getInstruction(line).runInstruction();
    }
}
