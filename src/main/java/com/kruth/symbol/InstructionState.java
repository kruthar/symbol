package com.kruth.symbol;

import com.kruth.symbol.expression.Expression;
import com.kruth.symbol.lexers.LineLexer;
import com.kruth.symbol.literals.Literal;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by kruthar on 3/15/16.
 */
public class InstructionState {
    private boolean inComment = false;
    private LineLexer lineLexer = null;
    private static InstructionState instructionState = null;

    private Stack<Integer> loopStack = new Stack<>();
    private Map<String, Literal> variableMap = new HashMap<>();

    protected InstructionState() {}

    public static InstructionState getInstance() {
        if (instructionState == null) {
            instructionState = new InstructionState();
        }
        return instructionState;
    }

    public void setComment(boolean set) {
        inComment = set;
    }

    public boolean getComment() {
        return inComment;
    }

    public void setVariable(String name, Literal value) {
        variableMap.put(name, value);
    }

    public Literal getVariable(String name) {
        return variableMap.get(name);
    }

    public boolean hasVariable(String name) {
        return variableMap.containsKey(name) && variableMap.get(name) != null;
    }

    public void setLineLexerFile(String filename) {
        lineLexer = new LineLexer(new File(getClass().getClassLoader().getResource(filename).getFile()));
    }

    public void setLineLexerString(String line) {
        lineLexer = new LineLexer(line);
    }

    public Boolean hasNextLine() {
        return lineLexer.hasNext();
    }

    public String nextLine() {
        return lineLexer.next();
    }

    public String peekNextLine() {
        return lineLexer.peek();
    }

    public void pushCurrentLoopMarker() {
        loopStack.push(lineLexer.getIndex());
    }

    public void resetToCurrentLoopMarker() {
        lineLexer.setIndex(loopStack.peek());
    }

    public void popCurrentLoopMarker() {
        if (!loopStack.empty()) {
            loopStack.pop();
        }
    }
}
