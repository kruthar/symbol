package com.kruth.symbol.instructions;

import com.kruth.symbol.Symbol;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 3/29/16.
 */
public class TestPrint {
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
    public void testPrint() {
        Symbol.executeLine("print one two three");
        assertEquals("Simple print test", "123", outStream.toString());
    }
}
