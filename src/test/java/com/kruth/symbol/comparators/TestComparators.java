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
    public void testSymbolStringComparators() {
        SymbolString string1 = new SymbolString("hello");
        SymbolString string2 = new SymbolString("world");

        assertEquals("String equality false", false, string1.equals(string2));
        assertEquals("String equality true", true, string1.equals(string1));

        assertEquals("String greater than true", true, string2.greaterThan(string1).getValue());
        assertEquals("String greater than false", false, string1.greaterThan(string2).getValue());
        assertEquals("String greater than or equal to greater true", true, string2.greaterThanOrEqualTo(string1).getValue());
        assertEquals("String greater than or equal to equal true", true, string2.greaterThanOrEqualTo(string2).getValue());
        assertEquals("String greater than or equal to greater false", false, string1.greaterThanOrEqualTo(string2).getValue());

        assertEquals("String less than true", true, string1.lessThan(string2).getValue());
        assertEquals("String less than true", false, string2.lessThan(string1).getValue());
        assertEquals("String less than or equal to less true", true, string1.lessThanOrEqualTo(string2).getValue());
        assertEquals("String less than or equal to equal true", true, string1.lessThanOrEqualTo(string1).getValue());
        assertEquals("String less than or equal to less false", false, string2.lessThanOrEqualTo(string1).getValue());
    }

    @Test
    public void testSymbolNumberComparators() {
        SymbolNumber num1 = new SymbolNumber(1);
        SymbolNumber num2 = new SymbolNumber(2);

        assertEquals("Number equality false", false, num1.equals(num2));
        assertEquals("Number equality true", true, num1.equals(num1));

        assertEquals("Number greater than true", true, num2.greaterThan(num1).getValue());
        assertEquals("Number greater than false", false, num1.greaterThan(num2).getValue());
        assertEquals("Number greater than or equal to greater true", true, num2.greaterThanOrEqualTo(num1).getValue());
        assertEquals("Number greater than or equal to equal true", true, num2.greaterThanOrEqualTo(num2).getValue());
        assertEquals("Number greater than or equal to greater false", false, num1.greaterThanOrEqualTo(num2).getValue());

        assertEquals("Number less than true", true, num1.lessThan(num2).getValue());
        assertEquals("Number less than false", false, num2.lessThan(num1).getValue());
        assertEquals("Number less than or equal to less true", true, num1.lessThanOrEqualTo(num2).getValue());
        assertEquals("Number less than or equal to equal true", true, num1.lessThanOrEqualTo(num1).getValue());
        assertEquals("Number less than or equal to less false", false, num2.lessThanOrEqualTo(num1).getValue());
    }

    @Test
    public void testSymbolBooleanComparators() {
        SymbolBoolean bool1 = new SymbolBoolean(true);
        SymbolBoolean bool2 = new SymbolBoolean(false);

        assertEquals("Boolean equality false", false, bool1.equals(bool2));
        assertEquals("Boolean equality true", true, bool1.equals(bool1));

        assertEquals("Boolean greater than true", true, bool1.greaterThan(bool2).getValue());
        assertEquals("Boolean greater than false", false, bool2.greaterThan(bool1).getValue());
        assertEquals("Boolean greater than or equal to greater true", true, bool1.greaterThanOrEqualTo(bool2).getValue());
        assertEquals("Boolean greater than or equal to equal true", true, bool1.greaterThanOrEqualTo(bool1).getValue());
        assertEquals("Boolean greater than or equal to greater false", false, bool2.greaterThanOrEqualTo(bool1).getValue());

        assertEquals("Boolean less than true", true, bool2.lessThan(bool1).getValue());
        assertEquals("Boolean less than false", false, bool1.lessThan(bool2).getValue());
        assertEquals("Boolean less than or equal to less true", true, bool2.lessThanOrEqualTo(bool1).getValue());
        assertEquals("Boolean less than or equal to equal true", true, bool2.lessThanOrEqualTo(bool2).getValue());
        assertEquals("Boolean less than or equal to less false", false, bool1.lessThanOrEqualTo(bool2).getValue());
    }
}
