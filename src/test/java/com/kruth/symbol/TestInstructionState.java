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
        Symbol.executeLine("set string1 string hello string");
        Symbol.executeLine("set string2 string world string");
        Symbol.executeLine("set num1 one");
        Symbol.executeLine("set num2 two");

        Symbol.executeLine("print string1 plus string2");
        assertEquals("String variable addition", "helloworld", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print num1 plus num2");
        assertEquals("Number variable addition", "3", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print string1 plus three four");
        assertEquals("String variable and literal addition", "hello34", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print num1 plus five");
        assertEquals("Number variable and literal addition", "6", outStream.toString());
        outStream.reset();
    }
}
