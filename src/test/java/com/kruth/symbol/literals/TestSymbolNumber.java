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

        assertEquals("compareTo equals, 1 == 1", 0, one.compareTo(one));
        assertEquals("compareTo greater than, 2 > 1", 1, two.compareTo(one));
        assertEquals("compareTo less than, 1 < 2", -1, one.compareTo(two));
    }

    @Test
    public void testComplexNumbers() {
        assertEquals("test 10", 10, new SymbolNumber(new SpaceLexer("ten")).getValue());
        assertEquals("test 11", 11, new SymbolNumber(new SpaceLexer("eleven")).getValue());
        assertEquals("test 12", 12, new SymbolNumber(new SpaceLexer("twelve")).getValue());
        assertEquals("test 13", 13, new SymbolNumber(new SpaceLexer("thirteen")).getValue());
        assertEquals("test 14", 14, new SymbolNumber(new SpaceLexer("fourteen")).getValue());
        assertEquals("test 15", 15, new SymbolNumber(new SpaceLexer("fifteen")).getValue());
        assertEquals("test 16", 16, new SymbolNumber(new SpaceLexer("sixteen")).getValue());
        assertEquals("test 17", 17, new SymbolNumber(new SpaceLexer("seventeen")).getValue());
        assertEquals("test 18", 18, new SymbolNumber(new SpaceLexer("eighteen")).getValue());
        assertEquals("test 19", 19, new SymbolNumber(new SpaceLexer("nineteen")).getValue());

        assertEquals("test 20", 20, new SymbolNumber(new SpaceLexer("twenty")).getValue());
        assertEquals("test 31", 31, new SymbolNumber(new SpaceLexer("thirty one")).getValue());
        assertEquals("test 42", 42, new SymbolNumber(new SpaceLexer("forty two")).getValue());
        assertEquals("test 53", 53, new SymbolNumber(new SpaceLexer("fifty three")).getValue());
        assertEquals("test 64", 64, new SymbolNumber(new SpaceLexer("sixty four")).getValue());
        assertEquals("test 75", 75, new SymbolNumber(new SpaceLexer("seventy five")).getValue());
        assertEquals("test 86", 86, new SymbolNumber(new SpaceLexer("eighty six")).getValue());
        assertEquals("test 97", 97, new SymbolNumber(new SpaceLexer("ninety seven")).getValue());

        assertEquals("test 100", 100, new SymbolNumber(new SpaceLexer("one hundred")).getValue());
        assertEquals("test 1200", 1200, new SymbolNumber(new SpaceLexer("twelve hundred")).getValue());
        assertEquals("test 2000", 2000, new SymbolNumber(new SpaceLexer("twenty hundred")).getValue());
        assertEquals("test 3400", 3400, new SymbolNumber(new SpaceLexer("thirty four hundred")).getValue());

        assertEquals("test 1000", 1000, new SymbolNumber(new SpaceLexer("one thousand")).getValue());
        assertEquals("test 11000", 11000, new SymbolNumber(new SpaceLexer("eleven thousand")).getValue());
        assertEquals("test 38000", 38000, new SymbolNumber(new SpaceLexer("thirty eight thousand")).getValue());
        assertEquals("test 100000", 100000, new SymbolNumber(new SpaceLexer("one hundred thousand")).getValue());
        assertEquals("test 101000", 101000, new SymbolNumber(new SpaceLexer("one hundred one thousand")).getValue());
        assertEquals("test 212000", 212000, new SymbolNumber(new SpaceLexer("two hundred twelve thousand")).getValue());
        assertEquals("test 360000", 360000, new SymbolNumber(new SpaceLexer("three hundred sixty thousand")).getValue());
        assertEquals("test 491000", 491000, new SymbolNumber(new SpaceLexer("four hundred ninety one thousand")).getValue());

        assertEquals("test 1001", 1001, new SymbolNumber(new SpaceLexer("one thousand one")).getValue());
        assertEquals("test 12013", 12013, new SymbolNumber(new SpaceLexer("twelve thousand thirteen")).getValue());
        assertEquals("test 20030", 20030, new SymbolNumber(new SpaceLexer("twenty thousand thirty")).getValue());
        assertEquals("test 100045", 100045, new SymbolNumber(new SpaceLexer("one hundred thousand forty five")).getValue());
        assertEquals("test 205100", 205100, new SymbolNumber(new SpaceLexer("two hundred five thousand one hundred")).getValue());
        assertEquals("test 318208", 318208, new SymbolNumber(new SpaceLexer("three hundred eighteen thousand two hundred eight")).getValue());
        assertEquals("test 490315", 490315, new SymbolNumber(new SpaceLexer("four hundred ninety thousand three hundred fifteen")).getValue());
        assertEquals("test 500420", 500420, new SymbolNumber(new SpaceLexer("five hundred thousand four hundred twenty")).getValue());
        assertEquals("test 600532", 600532, new SymbolNumber(new SpaceLexer("six hundred thousand five hundred thirty two")).getValue());

        assertEquals("test 1,001,001", 1001001, new SymbolNumber(new SpaceLexer("one million one thousand one")).getValue());
        assertEquals("test 16,012,013", 16012013, new SymbolNumber(new SpaceLexer("sixteen million twelve thousand thirteen")).getValue());
        assertEquals("test 80,020,030", 80020030, new SymbolNumber(new SpaceLexer("eighty million twenty thousand thirty")).getValue());
        assertEquals("test 100,100,045", 100100045, new SymbolNumber(new SpaceLexer("one hundred million one hundred thousand forty five")).getValue());
        assertEquals("test 203,205,100", 203205100, new SymbolNumber(new SpaceLexer("two hundred three million two hundred five thousand one hundred")).getValue());
        assertEquals("test 314,318,208", 314318208, new SymbolNumber(new SpaceLexer("three hundred fourteen million three hundred eighteen thousand two hundred eight")).getValue());
        assertEquals("test 450,490,315", 450490315, new SymbolNumber(new SpaceLexer("four hundred fifty million four hundred ninety thousand three hundred fifteen")).getValue());
        assertEquals("test 572,500,420", 572500420, new SymbolNumber(new SpaceLexer("five hundred seventy two million five hundred thousand four hundred twenty")).getValue());

        assertEquals("test 1,314,318,208", 1314318208, new SymbolNumber(new SpaceLexer("one billion three hundred fourteen million three hundred eighteen thousand two hundred eight")).getValue());
    }
}
