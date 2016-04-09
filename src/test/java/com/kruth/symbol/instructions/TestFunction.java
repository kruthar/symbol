package com.kruth.symbol.instructions;

import com.kruth.symbol.Symbol;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 4/3/16.
 */
public class TestFunction {
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
    public void testFunctionExecute() throws VariableDoesNotExistsException {
        Symbol.executeFile("testFunctionExecute.symb");
        assertEquals("Test function calls from execute instruction", "2\n", outStream.toString());
    }

    @Test
    public void testFunctionReturns() throws VariableDoesNotExistsException {
        Symbol.executeFile("testFunctionReturns.symb");
        assertEquals("Test function calls returns as expressions", "2\n6\n3\ntest\nnull\n", outStream.toString());
    }
}
