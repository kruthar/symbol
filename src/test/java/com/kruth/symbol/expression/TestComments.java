package com.kruth.symbol.expression;

import com.kruth.symbol.Symbol;
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
    public void testComment() {
        Symbol.executeFile("testComment.symb");
        assertEquals("Single line comment test", "World", outStream.toString());
        outStream.reset();
    }

    @Test
    public void testBlockComment() {
        Symbol.executeFile("testBlockComment.symb");
        assertEquals("Block comment test", "After", outStream.toString());
        outStream.reset();
    }
}
