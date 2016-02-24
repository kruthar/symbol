package com.kruth.symbol;

import com.kruth.symbol.lexers.LineLexer;

/**
 * Created by kruthar on 2/23/16.
 */
public class Symbol {
    public static void main(String args[]) {
        LineLexer lex = new LineLexer("test.symbol");

        while (lex.hasNext()) {
            InstructionRouter.getInstruction(lex, lex.next()).runInstruction();
        }
    }
}
