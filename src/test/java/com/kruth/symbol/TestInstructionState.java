package com.kruth.symbol;

import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.exceptions.VariableDoesNotExistsException;
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
    public void testVariables() throws SymbolException {
        Symbol.executeFile("instructionstate/testVariables.symb");
        assertEquals("Simple variable tests", "helloworld\n3\nhello34\n6\n", outStream.toString());
        outStream.reset();
    }

    @Test
    public void testScopePassThroughs() throws SymbolException {
        Symbol.executeFile("instructionstate/testForScope.symb");
        assertEquals("Test for loop scope pass through", "2", outStream.toString());
        outStream.reset();

        Symbol.executeFile("instructionstate/testForeachScope.symb");
        assertEquals("Test foreach loop scope pass through", "2", outStream.toString());
        outStream.reset();

        Symbol.executeFile("instructionstate/testFunctionScope.symb");
        assertEquals("Test function enclosed scope", "1", outStream.toString());
        outStream.reset();

        Symbol.executeFile("instructionstate/testIfScope.symb");
        assertEquals("Test if scope pass through", "2", outStream.toString());
        outStream.reset();

        Symbol.executeFile("instructionstate/testWhileScope.symb");
        assertEquals("Test while scope pass through", "2", outStream.toString());
        outStream.reset();
    }

    @Test(expected = VariableDoesNotExistsException.class)
    public void testForScope() throws SymbolException {
        Symbol.executeFile("instructionstate/testForScopeError.symb");
    }

    @Test(expected = VariableDoesNotExistsException.class)
    public void testForeachScope() throws SymbolException {
        Symbol.executeFile("instructionstate/testForeachScopeError.symb");
    }

    @Test(expected = VariableDoesNotExistsException.class)
    public void testFunctionScope() throws SymbolException {
        Symbol.executeFile("instructionstate/testFunctionScopeError.symb");
    }

    @Test(expected = VariableDoesNotExistsException.class)
    public void testIfScope() throws SymbolException {
        Symbol.executeFile("instructionstate/testIfScopeError.symb");
    }

    @Test(expected = VariableDoesNotExistsException.class)
    public void testWhileScope() throws SymbolException {
        Symbol.executeFile("instructionstate/testWhileScopeError.symb");
    }
}
