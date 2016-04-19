package com.kruth.symbol.instructions;

import com.kruth.symbol.Symbol;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 3/29/16.
 */
public class TestPrintln {
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
    public void TestPrintln() throws SymbolException {
        Symbol.executeLine("println one two three");
        assertEquals("Simple println test", "123\n", outStream.toString());
    }
}
