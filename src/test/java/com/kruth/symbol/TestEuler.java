package com.kruth.symbol;

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
    public void testEuler() {
        Symbol.executeFile("euler/euler1.symb");
        assertEquals("Euler #1", "233168", outStream.toString());
    }
}
