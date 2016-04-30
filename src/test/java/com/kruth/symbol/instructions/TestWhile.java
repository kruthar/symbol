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
 * Created by kruthar on 4/1/16.
 */
public class TestWhile {
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
    public void testWhile() throws SymbolException {
        Symbol.executeFile("testWhile.symb");
        assertEquals("Simple while test", "once\n0\n1\n2\ndone\n0,0\n0,1\n0,2\n1,0\n1,1\n1,2\n2,0\n2,1\n2,2\n", outStream.toString());
    }
}
