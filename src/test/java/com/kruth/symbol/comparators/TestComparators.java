package com.kruth.symbol.comparators;

import com.kruth.symbol.literals.SymbolBoolean;
import com.kruth.symbol.literals.SymbolNumber;
import com.kruth.symbol.literals.SymbolString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 3/30/16.
 */
public class TestComparators {
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
    public void testSymbolStringComparators() throws OperationNotSupportedException {
        SymbolString string1 = new SymbolString("hello");
        SymbolString string2 = new SymbolString("world");

        assertEquals("String equality false", false, ((SymbolBoolean) string1.equalTo(string2)).getValue());
        assertEquals("String equality true", true, ((SymbolBoolean) string1.equalTo(string1)).getValue());
        assertEquals("String inequality true", true, ((SymbolBoolean) string1.notEqualTo(string2)).getValue());
        assertEquals("String inequality false", false, ((SymbolBoolean) string1.notEqualTo(string1)).getValue());
    }

    @Test
    public void testSymbolNumberComparators() throws OperationNotSupportedException {
        SymbolNumber num1 = new SymbolNumber(1);
        SymbolNumber num2 = new SymbolNumber(2);

        assertEquals("Number equality false", false, ((SymbolBoolean) num1.equalTo(num2)).getValue());
        assertEquals("Number equality true", true, ((SymbolBoolean) num1.equalTo(num1)).getValue());
        assertEquals("Number inequality true", true, ((SymbolBoolean) num1.notEqualTo(num2)).getValue());
        assertEquals("Number inequality false", false, ((SymbolBoolean) num1.notEqualTo(num1)).getValue());
    }

    @Test
    public void testSymbolBooleanComparators() throws OperationNotSupportedException {
        SymbolBoolean bool1 = new SymbolBoolean(true);
        SymbolBoolean bool2 = new SymbolBoolean(false);

        assertEquals("Boolean equality false", false, ((SymbolBoolean) bool1.equalTo(bool2)).getValue());
        assertEquals("Boolean equality true", true, ((SymbolBoolean) bool1.equalTo(bool1)).getValue());
        assertEquals("Boolean inequality true", true, ((SymbolBoolean) bool1.notEqualTo(bool2)).getValue());
        assertEquals("Boolean inequality false", false, ((SymbolBoolean) bool1.notEqualTo(bool1)).getValue());
    }
}
