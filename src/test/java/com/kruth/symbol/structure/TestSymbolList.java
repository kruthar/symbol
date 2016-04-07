package com.kruth.symbol.structure;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.Literal;
import com.kruth.symbol.literals.SymbolNumber;
import com.kruth.symbol.structures.SymbolList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

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

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGet() {
        SymbolList list0 = new SymbolList(new ArrayList<SymbolObject>());
        SymbolList list1 = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(1)));

        assertEquals("Test get", new SymbolNumber(1), list1.get(new SymbolNumber(0)));

        // This should throw an exception
        list0.get(new SymbolNumber(1));
    }

    @Test
    public void testPut() {
        SymbolList list0 = new SymbolList(new ArrayList<SymbolObject>());
        SymbolList list1 = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(1)));
        SymbolList list2 = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(2), new SymbolNumber(1)));

        list0.put(new SymbolNumber(1));
        assertEquals("Simple put, no index", list1, list0);

        list0.put(new SymbolNumber(0), new SymbolNumber(2));
        assertEquals("put at 0", list2, list0);
    }

    @Test
    public void testSize() {
        SymbolList list1 = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(1)));

        assertEquals("Simple size test", new SymbolNumber(1), list1.size());
    }

    @Test
    public void testRemove() {
        SymbolList list0 = new SymbolList(new ArrayList<SymbolObject>());
        SymbolList list1 = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(1)));

        list1.remove(new SymbolNumber(0));
        assertEquals("Simple remove test", list0, list1);
    }
}