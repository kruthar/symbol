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
        assertEquals("Simple String expression", "Hello World!", outStream.toString());
        outStream.reset();
        Symbol.execute("print one two three");
        assertEquals("Simple Number expression", "123", outStream.toString());
        outStream.reset();
        Symbol.execute("print true");
        assertEquals("Simple Boolean expression", "true", outStream.toString());
    }

    @Test
    public void testSimpleMathExpressions() {
        // Numbers
        Symbol.execute("print one plus one");
        assertEquals("Simple addition test", "2", outStream.toString());
        outStream.reset();
        Symbol.execute("print two minus one");
        assertEquals("Simple subtraction test", "1", outStream.toString());
        outStream.reset();
        Symbol.execute("print two times three four");
        assertEquals("Simple multiplication test", "68", outStream.toString());
        outStream.reset();
        Symbol.execute("print four two dividedby three");
        assertEquals("Simple division test", "14", outStream.toString());
        outStream.reset();

        // Strings
        Symbol.execute("print stringstart Hello stringend plus stringstart World stringend");
        assertEquals("Simple String addition", "HelloWorld", outStream.toString());
        outStream.reset();

        // Mixed Types
        Symbol.execute("print stringstart one stringend plus two");
        assertEquals("String + Number", "one2", outStream.toString());
        outStream.reset();
        Symbol.execute("print three plus stringstart six stringend");
        assertEquals("Number + String", "3six", outStream.toString());
    }

    @Test
    public void testOrderOfOperations() {
        Symbol.execute("print one plus one times two");
        assertEquals("Multiplication before Addition", "3", outStream.toString());
        outStream.reset();
        Symbol.execute("print three minus one times two");
        assertEquals("Multiplication before Subtraction", "1", outStream.toString());
        outStream.reset();
        Symbol.execute("print two plus four dividedby two");
        assertEquals("Division before Addition", "4", outStream.toString());
        outStream.reset();
        Symbol.execute("print six minus two dividedby two");
        assertEquals("Multiplication before Addition", "5", outStream.toString());
        outStream.reset();
    }
}
