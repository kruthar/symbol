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

        assertEquals("String greater than true", true, ((SymbolBoolean) string2.greaterThan(string1)).getValue());
        assertEquals("String greater than false", false, ((SymbolBoolean) string1.greaterThan(string2)).getValue());
        assertEquals("String greater than or equal to greater true", true, ((SymbolBoolean) string2.greaterThanOrEqualTo(string1)).getValue());
        assertEquals("String greater than or equal to equal true", true, ((SymbolBoolean) string2.greaterThanOrEqualTo(string2)).getValue());
        assertEquals("String greater than or equal to greater false", false, ((SymbolBoolean) string1.greaterThanOrEqualTo(string2)).getValue());

        assertEquals("String less than true", true, ((SymbolBoolean) string1.lessThan(string2)).getValue());
        assertEquals("String less than true", false, ((SymbolBoolean) string2.lessThan(string1)).getValue());
        assertEquals("String less than or equal to less true", true, ((SymbolBoolean) string1.lessThanOrEqualTo(string2)).getValue());
        assertEquals("String less than or equal to equal true", true, ((SymbolBoolean) string1.lessThanOrEqualTo(string1)).getValue());
        assertEquals("String less than or equal to less false", false, ((SymbolBoolean) string2.lessThanOrEqualTo(string1)).getValue());
    }

    @Test
    public void testSymbolNumberComparators() throws OperationNotSupportedException {
        SymbolNumber num1 = new SymbolNumber(1);
        SymbolNumber num2 = new SymbolNumber(2);

        assertEquals("Number equality false", false, ((SymbolBoolean) num1.equalTo(num2)).getValue());
        assertEquals("Number equality true", true, ((SymbolBoolean) num1.equalTo(num1)).getValue());

        assertEquals("Number inequality true", true, ((SymbolBoolean) num1.notEqualTo(num2)).getValue());
        assertEquals("Number inequality false", false, ((SymbolBoolean) num1.notEqualTo(num1)).getValue());

        assertEquals("Number greater than true", true, ((SymbolBoolean) num2.greaterThan(num1)).getValue());
        assertEquals("Number greater than false", false, ((SymbolBoolean) num1.greaterThan(num2)).getValue());
        assertEquals("Number greater than or equal to greater true", true, ((SymbolBoolean) num2.greaterThanOrEqualTo(num1)).getValue());
        assertEquals("Number greater than or equal to equal true", true, ((SymbolBoolean) num2.greaterThanOrEqualTo(num2)).getValue());
        assertEquals("Number greater than or equal to greater false", false, ((SymbolBoolean) num1.greaterThanOrEqualTo(num2)).getValue());

        assertEquals("Number less than true", true, ((SymbolBoolean) num1.lessThan(num2)).getValue());
        assertEquals("Number less than false", false, ((SymbolBoolean) num2.lessThan(num1)).getValue());
        assertEquals("Number less than or equal to less true", true, ((SymbolBoolean) num1.lessThanOrEqualTo(num2)).getValue());
        assertEquals("Number less than or equal to equal true", true, ((SymbolBoolean) num1.lessThanOrEqualTo(num1)).getValue());
        assertEquals("Number less than or equal to less false", false, ((SymbolBoolean) num2.lessThanOrEqualTo(num1)).getValue());
    }

    @Test
    public void testSymbolBooleanComparators() throws OperationNotSupportedException {
        SymbolBoolean bool1 = new SymbolBoolean(true);
        SymbolBoolean bool2 = new SymbolBoolean(false);

        assertEquals("Boolean equality false", false, ((SymbolBoolean) bool1.equalTo(bool2)).getValue());
        assertEquals("Boolean equality true", true, ((SymbolBoolean) bool1.equalTo(bool1)).getValue());

        assertEquals("Boolean inequality true", true, ((SymbolBoolean) bool1.notEqualTo(bool2)).getValue());
        assertEquals("Boolean inequality false", false, ((SymbolBoolean) bool1.notEqualTo(bool1)).getValue());

        assertEquals("Boolean greater than true", true, ((SymbolBoolean) bool1.greaterThan(bool2)).getValue());
        assertEquals("Boolean greater than false", false, ((SymbolBoolean) bool2.greaterThan(bool1)).getValue());
        assertEquals("Boolean greater than or equal to greater true", true, ((SymbolBoolean) bool1.greaterThanOrEqualTo(bool2)).getValue());
        assertEquals("Boolean greater than or equal to equal true", true, ((SymbolBoolean) bool1.greaterThanOrEqualTo(bool1)).getValue());
        assertEquals("Boolean greater than or equal to greater false", false, ((SymbolBoolean) bool2.greaterThanOrEqualTo(bool1)).getValue());

        assertEquals("Boolean less than true", true, ((SymbolBoolean) bool2.lessThan(bool1)).getValue());
        assertEquals("Boolean less than false", false, ((SymbolBoolean) bool1.lessThan(bool2)).getValue());
        assertEquals("Boolean less than or equal to less true", true, ((SymbolBoolean) bool2.lessThanOrEqualTo(bool1)).getValue());
        assertEquals("Boolean less than or equal to equal true", true, ((SymbolBoolean) bool2.lessThanOrEqualTo(bool2)).getValue());
        assertEquals("Boolean less than or equal to less false", false, ((SymbolBoolean) bool1.lessThanOrEqualTo(bool2)).getValue());
    }
}
