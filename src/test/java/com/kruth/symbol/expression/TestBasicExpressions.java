package com.kruth.symbol.expression;

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
public class TestBasicExpressions {
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
    public void testSimpleExpressions() {
        Symbol.execute("print stringstart Hello World! stringend");
        assertEquals("Simple String expression", "Hello World!\n", outStream.toString());
        outStream.reset();
        Symbol.execute("print one two three");
        assertEquals("Simple Number expression", "123\n", outStream.toString());
        outStream.reset();
        Symbol.execute("print true");
        assertEquals("Simple Boolean expression", "true\n", outStream.toString());
    }
}
