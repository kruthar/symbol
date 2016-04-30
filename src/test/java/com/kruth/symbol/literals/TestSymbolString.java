package com.kruth.symbol.literals;

import com.kruth.symbol.exceptions.SymbolException;
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
    public void testSubstring() {
        String testString = "helloworld";
        SymbolString string1 = new SymbolString(testString);
        assertEquals("Test substring with start", new SymbolString("loworld"), ((LanguageString) string1.getLanguageObject()).substring(new SymbolNumber(3)));
        assertEquals("Test substring with start and end", new SymbolString("lowo"), ((LanguageString) string1.getLanguageObject()).substring(new SymbolNumber(3), new SymbolNumber(7)));
    }

    @Test
    public void testTonumber() throws SymbolException {
        String testString = "one two three four";
        SymbolString string1 = new SymbolString(testString);
        assertEquals("Test string tonumber", new SymbolNumber(1234), ((LanguageString) string1.getLanguageObject()).tonumber());

        String frontstring = "01";
        SymbolString string2 = new SymbolString(frontstring);
        assertEquals("Test string tonumber frontloaded", new SymbolNumber(1), ((LanguageString) string2.getLanguageObject()).tonumber());
    }

    @Test
    public void testIndexof() {
        String testString = "helloworld";
        SymbolString string1 = new SymbolString(testString);
        assertEquals("Test string index of 'o'", new SymbolNumber(4), ((LanguageString) string1.getLanguageObject()).indexof(new SymbolString("o")));
    }

    @Test
    public void testReplace() {
        String testString = "helloworld";
        SymbolString string1 = new SymbolString(testString);
        assertEquals("Test string replace 'llo' ''", new SymbolString("heworld"), ((LanguageString) string1.getLanguageObject()).replace(new SymbolString("llo"), new SymbolString("")));
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
