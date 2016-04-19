package com.kruth.symbol.expression;

import com.kruth.symbol.Symbol;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
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
    public void testSimpleExpressions() throws SymbolException {
        Symbol.executeLine("print string Hello World! string");
        assertEquals("Simple String expression", "Hello World!", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print one two three");
        assertEquals("Simple Number expression", "123", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print true");
        assertEquals("Simple Boolean expression", "true", outStream.toString());
    }

    @Test
    public void testBooleanOperators() throws SymbolException {
        Symbol.executeLine("print true or true");
        assertEquals("true || true == true", "true", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print true or false");
        assertEquals("true || false == true", "true", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print false or false");
        assertEquals("false || false == false", "false", outStream.toString());
        outStream.reset();

        Symbol.executeLine("print true and true");
        assertEquals("true && true == true", "true", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print true and false");
        assertEquals("true && false == false", "false", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print false and false");
        assertEquals("false && false == false", "false", outStream.toString());
        outStream.reset();
    }

    @Test
    public void testSimpleMathExpressions() throws SymbolException {
        // Numbers
        Symbol.executeLine("print one plus one");
        assertEquals("Simple addition test", "2", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print two minus one");
        assertEquals("Simple subtraction test", "1", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print two times three four");
        assertEquals("Simple multiplication test", "68", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print four two dividedby three");
        assertEquals("Simple division test", "14", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print eight modulo three");
        assertEquals("Simple modulo test", "2", outStream.toString());
        outStream.reset();

        // Strings
        Symbol.executeLine("print string Hello string plus string World string");
        assertEquals("Simple String addition", "HelloWorld", outStream.toString());
        outStream.reset();

        // Mixed Types
        Symbol.executeLine("print string one string plus two");
        assertEquals("String + Number", "one2", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print three plus string six string");
        assertEquals("Number + String", "3six", outStream.toString());
    }

    @Test
    public void testOrderOfOperations() throws SymbolException {
        Symbol.executeLine("print one plus one times two");
        assertEquals("Multiplication before Addition", "3", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print three minus one times two");
        assertEquals("Multiplication before Subtraction", "1", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print two plus four dividedby two");
        assertEquals("Division before Addition", "4", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print six minus two dividedby two");
        assertEquals("Division before Addition", "5", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print six plus two modulo two");
        assertEquals("Modulo before Addition", "6", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print six minus two modulo two");
        assertEquals("Modulo before Subtraction", "6", outStream.toString());
        outStream.reset();
    }

    @Test
    public void testParenthesisOperations() throws SymbolException {
        Symbol.executeLine("print open one plus one close times two");
        assertEquals("Parenthesis Multiplication before Addition", "4", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print open three minus one close times two");
        assertEquals("Parenthesis Multiplication before Subtraction", "4", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print open two plus four close dividedby two");
        assertEquals("Parenthesis Division before Addition", "3", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print open six minus two close dividedby two");
        assertEquals("Parenthesis Multiplication before Addition", "2", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print open six plus two close modulo two");
        assertEquals("Parenthesis modulo before Addition", "0", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print open six minus two close modulo two");
        assertEquals("Parenthesis modulo before Subtraction", "0", outStream.toString());
        outStream.reset();
    }

    @Test
    public void testComparators() throws SymbolException {
        Symbol.executeLine("print one equals one");
        assertEquals("Equality positive test", "true", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print string hello string equals string world string");
        assertEquals("Equality negative test", "false", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print string hello string notequals string world string");
        assertEquals("Inequality positive test", "true", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print one notequals one");
        assertEquals("Inequality negative test", "false", outStream.toString());
        outStream.reset();

        Symbol.executeLine("print two greaterthan one");
        assertEquals("Greater than positive test", "true", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print one greaterthan two");
        assertEquals("Greater than negative test", "false", outStream.toString());
        outStream.reset();

        Symbol.executeLine("print one lessthan two");
        assertEquals("Less than positive test", "true", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print two lessthan one");
        assertEquals("Less than negative test", "false", outStream.toString());
        outStream.reset();

        Symbol.executeLine("print two greaterthanequals one");
        assertEquals("Greater than equals greater positive test", "true", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print one greaterthanequals one");
        assertEquals("Greater than equals equals positive test", "true", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print one greaterthanequals two");
        assertEquals("Greater than equals negative test", "false", outStream.toString());
        outStream.reset();

        Symbol.executeLine("print one lessthanequals two");
        assertEquals("Less than equals less positive test", "true", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print one lessthanequals one");
        assertEquals("Less than equals equals positive test", "true", outStream.toString());
        outStream.reset();
        Symbol.executeLine("print two lessthanequals one");
        assertEquals("Less than equals negative test", "false", outStream.toString());
        outStream.reset();
    }
}
