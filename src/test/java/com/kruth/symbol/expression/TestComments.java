package com.kruth.symbol.expression;

import com.kruth.symbol.Symbol;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 3/30/16.
 */
public class TestComments {
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
    public void testComment() throws VariableDoesNotExistsException {
        Symbol.executeFile("testComment.symb");
        assertEquals("Single line comment test", "World", outStream.toString());
        outStream.reset();
    }

    @Test
    public void testBlockComment() throws VariableDoesNotExistsException {
        Symbol.executeFile("testBlockComment.symb");
        assertEquals("Block comment test", "After\n1001000\n1000000000\nafter\n123\n", outStream.toString());
        outStream.reset();
    }
}
