package com.kruth.symbol;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 3/30/16.
 */
public class TestInstructionState {
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
    public void testVariables() {
        Symbol.executeFile("testVariables.symb");
        assertEquals("Simple variable tests", "helloworld\n3\nhello34\n6\n", outStream.toString());
        outStream.reset();
    }
}
