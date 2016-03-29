package com.kruth.symbol.literals;

import com.kruth.symbol.lexers.SpaceLexer;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 3/27/16.
 */
public class TestSymbolBoolean {
    @Test
    public void testConstructors() {
        SymbolBoolean bool1 = new SymbolBoolean(false);
        assertEquals("Boolean constructor: false", false, bool1.getValue());

        SymbolBoolean bool2 = new SymbolBoolean(true);
        assertEquals("Boolean constructor: true", true, bool2.getValue());

        SymbolBoolean bool3 = new SymbolBoolean(new SpaceLexer("false"));
        assertEquals("SpaceLexer constructor: false", false, bool3.getValue());

        SymbolBoolean bool4 = new SymbolBoolean(new SpaceLexer("true"));
        assertEquals("SpaceLexer constructor: true", true, bool4.getValue());

        SymbolBoolean bool5 = new SymbolBoolean(new SpaceLexer("not false"));
        assertEquals("SpaceLexer constructor: not false", true, bool5.getValue());

        SymbolBoolean bool6 = new SymbolBoolean(new SpaceLexer("not true"));
        assertEquals("SpaceLexer constructor: not true", false, bool6.getValue());
    }

    @Test
    public void testPlus() {
        SymbolBoolean boolTrue = new SymbolBoolean(true);
        SymbolBoolean boolFalse = new SymbolBoolean(false);

        assertEquals("false + false = false", false, ((SymbolBoolean) boolFalse.plus(boolFalse)).getValue());
        assertEquals("false + true = true", true, ((SymbolBoolean) boolFalse.plus(boolTrue)).getValue());
        assertEquals("true + false = true", true, ((SymbolBoolean) boolTrue.plus(boolFalse)).getValue());
        assertEquals("true + true = false", true, ((SymbolBoolean) boolTrue.plus(boolTrue)).getValue());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testMinus() throws OperationNotSupportedException {
        SymbolBoolean boolTrue = new SymbolBoolean(true);
        SymbolBoolean boolFalse = new SymbolBoolean(false);

        boolTrue.minus(boolFalse);
    }

    @Test
    public void testTimes() {
        SymbolBoolean boolTrue = new SymbolBoolean(true);
        SymbolBoolean boolFalse = new SymbolBoolean(false);

        assertEquals("false * false = false", false, ((SymbolBoolean) boolFalse.times(boolFalse)).getValue());
        assertEquals("false * true = false", false, ((SymbolBoolean) boolFalse.times(boolTrue)).getValue());
        assertEquals("true * false = false", false, ((SymbolBoolean) boolTrue.times(boolFalse)).getValue());
        assertEquals("true * true = true", true, ((SymbolBoolean) boolTrue.times(boolTrue)).getValue());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testDividedBy() throws OperationNotSupportedException {
        SymbolBoolean boolTrue = new SymbolBoolean(true);
        SymbolBoolean boolFalse = new SymbolBoolean(false);

        boolTrue.dividedby(boolFalse);
    }

    @Test
    public void testCompareTo() {
        SymbolBoolean boolTrue = new SymbolBoolean(true);
        SymbolBoolean boolFalse = new SymbolBoolean(false);

        try {
            assertEquals("true == true", 0, boolTrue.comparedTo(boolTrue));
            assertEquals("true != false", 1, boolTrue.comparedTo(boolFalse));
            assertEquals("false != true", -1, boolFalse.comparedTo(boolTrue));
            assertEquals("false == false", 0, boolFalse.comparedTo(boolFalse));
        } catch (OperationNotSupportedException e) {
            fail("Failed to compare SymbolBooleans.");
        }
    }
}
