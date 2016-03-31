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
        Symbol.execute("set string1 string hello string");
        Symbol.execute("set string2 string world string");
        Symbol.execute("set num1 one");
        Symbol.execute("set num2 two");

        Symbol.execute("print string1 plus string2");
        assertEquals("String variable addition", "helloworld", outStream.toString());
        outStream.reset();
        Symbol.execute("print num1 plus num2");
        assertEquals("Number variable addition", "3", outStream.toString());
        outStream.reset();
        Symbol.execute("print string1 plus three four");
        assertEquals("String variable and literal addition", "hello34", outStream.toString());
        outStream.reset();
        Symbol.execute("print num1 plus five");
        assertEquals("Number variable and literal addition", "6", outStream.toString());
        outStream.reset();
    }
}
