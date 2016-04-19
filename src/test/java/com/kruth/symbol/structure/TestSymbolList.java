package com.kruth.symbol.structure;

import com.kruth.symbol.InstructionState;
import com.kruth.symbol.Symbol;
import com.kruth.symbol.SymbolObject;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.SymbolListIndexOutOfBoundsException;
import com.kruth.symbol.exceptions.UnexpectedKeywordException;
import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.literals.Literal;
import com.kruth.symbol.literals.SymbolNumber;
import com.kruth.symbol.structures.SymbolList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 4/4/16.
 */
public class TestSymbolList {
    private final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

    @Before
    public void setupStreams() {
        System.setOut(new PrintStream(outStream));
    }

    @After
    public void cleanupStreams() {
        System.setOut(null);
    }

    @Test
    public void testSymbolListExpressions() throws SymbolException {
        Symbol.executeFile("testSymbolList.symb");
        assertEquals("SymbolList expressions", "[1, 2, 3]\n[4, 1, 2, 3]\n[4, 1, 2, 3, 5]\n5\n[4, 2, 3, 5]\n[2, 3, 5]\ninside\n", outStream.toString());
        outStream.reset();
    }

    @Test
    public void testConstructors() throws SymbolException {
        SymbolList objectConstructor = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(1), new SymbolNumber(2)));
        assertEquals("Test list constructor", new ArrayList<SymbolObject>(Arrays.asList((Literal) new SymbolNumber(1), new SymbolNumber(2))), objectConstructor.getValue());

        SymbolList stringConstructor = new SymbolList(new InstructionState(), new SpaceLexer("list open one sep two close"));
        assertEquals("Test list constructor", new ArrayList<SymbolObject>(Arrays.asList((Literal) new SymbolNumber(1), new SymbolNumber(2))), stringConstructor.getValue());
    }

    @Test(expected = UnexpectedKeywordException.class)
    public void testOpenError() throws SymbolException {
        SymbolList list0 = new SymbolList(new InstructionState(), new SpaceLexer("list one sep two close"));
    }

    @Test(expected = UnexpectedKeywordException.class)
    public void testCloseError() throws SymbolException {
        SymbolList list0 = new SymbolList(new InstructionState(), new SpaceLexer("list open one sep two sep"));
    }

    @Test(expected = SymbolListIndexOutOfBoundsException.class)
    public void testGet() throws SymbolException {
        SymbolList list0 = new SymbolList(new ArrayList<SymbolObject>());
        SymbolList list1 = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(1)));

        assertEquals("Test get", new SymbolNumber(1), list1.get(new SymbolNumber(0)));

        // This should throw an exception
        list0.get(new SymbolNumber(1));
    }

    @Test(expected = SymbolListIndexOutOfBoundsException.class)
    public void testPut() throws SymbolException {
        SymbolList list0 = new SymbolList(new ArrayList<SymbolObject>());
        SymbolList list1 = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(1)));
        SymbolList list2 = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(2), new SymbolNumber(1)));

        list0.put(new SymbolNumber(1));
        assertEquals("Simple put, no index", list1, list0);

        list0.put(new SymbolNumber(0), new SymbolNumber(2));
        assertEquals("put at 0", list2, list0);

        list2.put(new SymbolNumber(5), new SymbolNumber(0));
    }

    @Test
    public void testSize() throws SymbolException {
        SymbolList list0 = new SymbolList(new InstructionState(), new SpaceLexer("list open close"));
        SymbolList list1 = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(1)));

        assertEquals("Empty size test", new SymbolNumber(0), list0.size());
        assertEquals("Simple size test", new SymbolNumber(1), list1.size());
    }

    @Test(expected = SymbolListIndexOutOfBoundsException.class)
    public void testRemove() throws SymbolException {
        SymbolList list0 = new SymbolList(new ArrayList<SymbolObject>());
        SymbolList list1 = new SymbolList(Arrays.asList((SymbolObject) new SymbolNumber(1)));

        list1.remove(new SymbolNumber(0));
        assertEquals("Simple remove test", list0, list1);

        list1.remove(new SymbolNumber(0));
    }
}
