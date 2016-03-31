package com.kruth.symbol.literals;

import com.kruth.symbol.lexers.SpaceLexer;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 3/28/16.
 */
public class TestSymbolNumber {
    @Test
    public void testConstructors() {
        SymbolNumber five = new SymbolNumber(5);
        assertEquals("Test SymbolNumber integer constructor", 5, five.getValue());

        SymbolNumber onetwothree = new SymbolNumber(new SpaceLexer("one two three"));
        assertEquals("Test SymbolNumber Space Lexer digit string constructor.", 123, onetwothree.getValue());
    }

    @Test
    public void testPlus() {
        SymbolNumber one = new SymbolNumber(1);
        SymbolNumber two = new SymbolNumber(2);

        assertEquals("Simple addition, 1 + 2 = 3", 3, ((SymbolNumber) one.plus(two)).getValue());
    }

    @Test
    public void testMinus() throws OperationNotSupportedException {
        SymbolNumber one = new SymbolNumber(1);
        SymbolNumber two = new SymbolNumber(2);

        assertEquals("Simple subtraction, 2 - 1 = 1", 1, ((SymbolNumber) two.minus(one)).getValue());
    }

    @Test
    public void testTimes() throws OperationNotSupportedException {
        SymbolNumber one = new SymbolNumber(1);
        SymbolNumber two = new SymbolNumber(2);

        assertEquals("Simple multiplication, 1 * 2 = 2", 2, ((SymbolNumber) one.times(two)).getValue());
    }

    @Test
    public void testDividedBy() throws OperationNotSupportedException {
        SymbolNumber one = new SymbolNumber(1);
        SymbolNumber two = new SymbolNumber(2);

        assertEquals("Simple division, 2 / 1 = 2", 2, ((SymbolNumber) two.dividedby(one)).getValue());
    }

    @Test
    public void testModulo() throws OperationNotSupportedException {
        SymbolNumber five = new SymbolNumber(5);
        SymbolNumber three = new SymbolNumber(3);

        assertEquals("Simple modulo, 5 % 3 = 2", 2, ((SymbolNumber) five.modulo(three)).getValue());
    }

    @Test
    public void testCompareTo() throws OperationNotSupportedException {
        SymbolNumber one = new SymbolNumber(1);
        SymbolNumber two = new SymbolNumber(2);

        assertEquals("compareTo equals, 1 == 1", 0, one.comparedTo(one));
        assertEquals("compareTo greater than, 2 > 1", 1, two.comparedTo(one));
        assertEquals("compareTo less than, 1 < 2", -1, one.comparedTo(two));
    }
}
