package com.kruth.symbol.literals;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.structures.SymbolList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 4/4/16.
 */
public class TestSymbolList {
    @Test
    public void testConstructors() {
        SymbolList objectConstructor = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(1), new SymbolNumber(2)));
        assertEquals("Test list constructor", new ArrayList<SymbolObject>(Arrays.asList((Literal) new SymbolNumber(1), new SymbolNumber(2))), objectConstructor.getValue());

        SymbolList stringConstructor = new SymbolList(new InstructionState(), new SpaceLexer("list one sep two sep list"));
        assertEquals("Test list constructor", new ArrayList<SymbolObject>(Arrays.asList((Literal) new SymbolNumber(1), new SymbolNumber(2))), stringConstructor.getValue());
    }

    @Test
    public void testPlus() {
        SymbolList one = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(1), new SymbolNumber(2)));
        SymbolList two = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(3), new SymbolNumber(4)));

        assertEquals("Simple addition, [1, 2] + [3, 4] = [1, 2, 3, 4]", new ArrayList<>(Arrays.asList((Literal) new SymbolNumber(1), new SymbolNumber(2), new SymbolNumber(3), new SymbolNumber(4))), one.plus(two).getValue());
    }
}
