package com.kruth.symbol.instructions;

import com.kruth.symbol.Symbol;
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
    public void testWhile() {
        Symbol.executeFile("testWhile.symb");
        assertEquals("Simple while test", "once\n0\n1\n2\ndone\n", outStream.toString());
    }
}
