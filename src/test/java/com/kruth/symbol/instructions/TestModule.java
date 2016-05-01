package com.kruth.symbol.instructions;

import com.kruth.symbol.Symbol;
import com.kruth.symbol.exceptions.SymbolException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 4/8/16.
 */
public class TestModule {
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
    public void testMathModule() throws SymbolException {
        Symbol.executeFile("testModule.symb");
        assertEquals("Test Simple Math Module", "3.14\n8\n3.14\n8\n1.41\n12.34\ntrue\ntrue\nfalse\ntrue\nfalse\ntrue\nfalse\n", outStream.toString());
    }
}
