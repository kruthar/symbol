package com.kruth.symbol;

import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 4/1/16.
 */
public class TestEuler {
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
    public void testEuler() throws SymbolException {
        Symbol.executeFile("euler/euler1.symb");
        assertEquals("Euler #1", "233168", outStream.toString());
        outStream.reset();
        Symbol.executeFile("euler/euler2.symb");
        assertEquals("Euler #2", "4613732", outStream.toString());
        outStream.reset();
    }
}
