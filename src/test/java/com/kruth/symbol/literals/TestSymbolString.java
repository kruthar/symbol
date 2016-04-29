package com.kruth.symbol.literals;

import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.structures.LanguageList;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 3/29/16.
 */
public class TestSymbolString {
    @Test
    public void testConstructors() {
        String testString = "helloworld";
        SymbolString string1 = new SymbolString(testString);
        assertEquals("Basic string constructor test", testString, string1.getValue());

        String testCode = String.format("string %s string", testString);
        SymbolString string2 = new SymbolString(new SpaceLexer(testCode));
        assertEquals("SpaceLexer constructor test", testString, string2.getValue());
    }

    @Test
    public void testLength() {
        String testString = "helloworld";
        SymbolString string1 = new SymbolString(testString);
        assertEquals("Test string length", new SymbolNumber(10), ((LanguageString) string1.getLanguageObject()).length());
    }

    @Test
    public void testPlus() {
        String testString1 = "hello";
        String testString2 = "world";
        SymbolString string1 = new SymbolString(testString1);
        SymbolString string2 = new SymbolString(testString2);

        assertEquals("'hello' + 'world' = 'helloworld'", testString1 + testString2, string1.plus(string2).getValue());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testMinus() throws OperationNotSupportedException {
        String testString1 = "no";
        String testString2 = "minus";
        SymbolString string1 = new SymbolString(testString1);
        SymbolString string2 = new SymbolString(testString2);

        string1.minus(string2);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testTimes() throws OperationNotSupportedException {
        String testString1 = "no";
        String testString2 = "times";
        SymbolString string1 = new SymbolString(testString1);
        SymbolString string2 = new SymbolString(testString2);

        string1.times(string2);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testDividedBy() throws OperationNotSupportedException {
        String testString1 = "no";
        String testString2 = "dividedby";
        SymbolString string1 = new SymbolString(testString1);
        SymbolString string2 = new SymbolString(testString2);

        string1.dividedby(string2);
    }
}
