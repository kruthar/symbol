package com.kruth.symbol;

import com.kruth.symbol.instructions.Instruction;
import com.kruth.symbol.lexers.LineLexer;

/**
 * Created by kruthar on 2/23/16.
 */
public class Symbol {
    public static void main(String args[]) {
        Symbol.executeFile("booleans.symb");
        //Symbol.execute("print stringstart true =  stringend plus true");
    }

    public static void executeFile(String filename) {
        LineLexer lex = new LineLexer(filename);

        while (lex.hasNext()) {
            execute(lex.next());
        }
    }

    public static void execute(String line) {
        Instruction instruction = InstructionRouter.getInstruction(line);
        if (instruction != null) {
            instruction.runInstruction();
        }
    }
}
