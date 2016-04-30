package com.kruth.symbol.literals;

import com.kruth.symbol.Symbol;
import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.lexers.SpaceLexer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 3/28/16.
 */
public class TestSymbolNumber {
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
    public void testConstructors() throws SymbolException {
        SymbolNumber five = new SymbolNumber(5);
        assertEquals("Test SymbolNumber integer constructor", BigDecimal.valueOf(5), five.getValue());

        SymbolNumber fivedotthree = new SymbolNumber(5.3);
        assertEquals("Test SymbolNumber decimal constructor", BigDecimal.valueOf(5.3), fivedotthree.getValue());

        SymbolNumber onetwothree = new SymbolNumber(new SpaceLexer("one two three"));
        assertEquals("Test SymbolNumber Space Lexer digit string constructor.", BigDecimal.valueOf(123), onetwothree.getValue());

        SymbolNumber onetwodotthree = new SymbolNumber(new SpaceLexer("one two dec three"));
        assertEquals("Test SymbolNumber Space Lexer decimal string constructor.", BigDecimal.valueOf(12.3), onetwodotthree.getValue());

        SymbolNumber dotthree = new SymbolNumber(new SpaceLexer("dec three"));
        assertEquals("Test SymbolNumber Space Lexer fraction decimal string constructor.", BigDecimal.valueOf(.3), dotthree.getValue());
    }

    @Test
    public void testToString() {
        SymbolNumber one = new SymbolNumber(123);

        assertEquals("Test tostring of 123", new SymbolString("123"), ((LanguageNumber) one.getLanguageObject()).tostring());
    }

    @Test
    public void testCeil() {
        SymbolNumber one = new SymbolNumber(10.2);

        assertEquals("Test ceil of 10.2", new SymbolNumber(11), ((LanguageNumber) one.getLanguageObject()).ceil());
    }

    @Test
    public void testFloor() {
        SymbolNumber one = new SymbolNumber(10.2);

        assertEquals("Test floor of 10.2", new SymbolNumber(10), ((LanguageNumber) one.getLanguageObject()).floor());
    }

    @Test
    public void testPlus() throws SymbolException{
        SymbolNumber one = new SymbolNumber(1);
        SymbolNumber two = new SymbolNumber(2);

        assertEquals("Simple addition, 1 + 2 = 3", BigDecimal.valueOf(3), one.plus(two).getValue());

        SymbolNumber dotone = new SymbolNumber(.1);
        SymbolNumber twodotthree = new SymbolNumber(2.3);

        assertEquals("Simple addition, .1 + 2.3 = 2.4", BigDecimal.valueOf(2.4), dotone.plus(twodotthree).getValue());

        Symbol.executeLine("print twenty plus one");
        assertEquals("Simple plus spoken test", "21", outStream.toString());
    }

    @Test
    public void testMinus() throws OperationNotSupportedException, SymbolException {
        SymbolNumber one = new SymbolNumber(1);
        SymbolNumber two = new SymbolNumber(2);

        assertEquals("Simple subtraction, 2 - 1 = 1", BigDecimal.valueOf(1), two.minus(one).getValue());

        SymbolNumber dotone = new SymbolNumber(.1);
        SymbolNumber twodotthree = new SymbolNumber(2.3);

        assertEquals("Simple addition, .1 - 2.3 = -2.2", BigDecimal.valueOf(-2.2), dotone.minus(twodotthree).getValue());

        Symbol.executeLine("print twenty minus one");
        assertEquals("Simple minus spoken test", "19", outStream.toString());
    }

    @Test
    public void testTimes() throws OperationNotSupportedException, SymbolException {
        SymbolNumber one = new SymbolNumber(1);
        SymbolNumber two = new SymbolNumber(2);

        assertEquals("Simple multiplication, 1 * 2 = 2", BigDecimal.valueOf(2), one.times(two).getValue());

        SymbolNumber dotone = new SymbolNumber(.1);
        SymbolNumber twodotthree = new SymbolNumber(2.3);

        assertEquals("Simple addition, .1 * 2.3 = .23", BigDecimal.valueOf(.23), dotone.times(twodotthree).getValue());

        Symbol.executeLine("print twenty times one");
        assertEquals("Simple times spoken test", "20", outStream.toString());
    }

    @Test
    public void testDividedBy() throws OperationNotSupportedException, SymbolException {
        SymbolNumber one = new SymbolNumber(1);
        SymbolNumber two = new SymbolNumber(2);

        assertEquals("Simple division, 2 / 1 = 2", BigDecimal.valueOf(2), two.dividedby(one).getValue());

        SymbolNumber dotone = new SymbolNumber(.1);
        SymbolNumber twodotthree = new SymbolNumber(2.3);

        assertEquals("Simple addition, 2.3 / .1 = 23", BigDecimal.valueOf(23), twodotthree.dividedby(dotone).getValue());

        Symbol.executeLine("print twenty dividedby one");
        assertEquals("Simple dividedby spoken test", "20", outStream.toString());
    }

    @Test
    public void testModulo() throws OperationNotSupportedException, SymbolException {
        SymbolNumber five = new SymbolNumber(5);
        SymbolNumber three = new SymbolNumber(3);

        assertEquals("Simple modulo, 5 % 3 = 2", BigDecimal.valueOf(2), five.modulo(three).getValue());

        SymbolNumber dottwo = new SymbolNumber(.2);
        SymbolNumber twodotthree = new SymbolNumber(2.3);

        assertEquals("Simple addition, 2.3 % .2 = .1", BigDecimal.valueOf(.1), twodotthree.modulo(dottwo).getValue());

        Symbol.executeLine("print twenty modulo one");
        assertEquals("Simple modulo spoken test", "0", outStream.toString());
    }

    @Test
    public void testCompareTo() throws OperationNotSupportedException {
        SymbolNumber one = new SymbolNumber(1);
        SymbolNumber two = new SymbolNumber(2);

        assertEquals("compareTo equals, 1 == 1", 0, one.compareTo(one));
        assertEquals("compareTo greater than, 2 > 1", 1, two.compareTo(one));
        assertEquals("compareTo less than, 1 < 2", -1, one.compareTo(two));

        SymbolNumber dotone = new SymbolNumber(.1);
        SymbolNumber dottwo = new SymbolNumber(.2);

        assertEquals("compareTo equals, .1 == .1", 0, dotone.compareTo(dotone));
        assertEquals("compareTo greater than, .2 > .1", 1, dottwo.compareTo(dotone));
        assertEquals("compareTo less than, .1 < .2", -1, dotone.compareTo(dottwo));
    }

    @Test
    public void testComplexNumbers() throws SymbolException {
        assertEquals("test 10", BigDecimal.valueOf(10), new SymbolNumber(new SpaceLexer("ten")).getValue());
        assertEquals("test 11", BigDecimal.valueOf(11), new SymbolNumber(new SpaceLexer("eleven")).getValue());
        assertEquals("test 12", BigDecimal.valueOf(12), new SymbolNumber(new SpaceLexer("twelve")).getValue());
        assertEquals("test 13", BigDecimal.valueOf(13), new SymbolNumber(new SpaceLexer("thirteen")).getValue());
        assertEquals("test 14", BigDecimal.valueOf(14), new SymbolNumber(new SpaceLexer("fourteen")).getValue());
        assertEquals("test 15", BigDecimal.valueOf(15), new SymbolNumber(new SpaceLexer("fifteen")).getValue());
        assertEquals("test 16", BigDecimal.valueOf(16), new SymbolNumber(new SpaceLexer("sixteen")).getValue());
        assertEquals("test 17", BigDecimal.valueOf(17), new SymbolNumber(new SpaceLexer("seventeen")).getValue());
        assertEquals("test 18", BigDecimal.valueOf(18), new SymbolNumber(new SpaceLexer("eighteen")).getValue());
        assertEquals("test 19", BigDecimal.valueOf(19), new SymbolNumber(new SpaceLexer("nineteen")).getValue());

        assertEquals("test 20", BigDecimal.valueOf(20), new SymbolNumber(new SpaceLexer("twenty")).getValue());
        assertEquals("test 31", BigDecimal.valueOf(31), new SymbolNumber(new SpaceLexer("thirty one")).getValue());
        assertEquals("test 42", BigDecimal.valueOf(42), new SymbolNumber(new SpaceLexer("forty two")).getValue());
        assertEquals("test 53", BigDecimal.valueOf(53), new SymbolNumber(new SpaceLexer("fifty three")).getValue());
        assertEquals("test 64", BigDecimal.valueOf(64), new SymbolNumber(new SpaceLexer("sixty four")).getValue());
        assertEquals("test 75", BigDecimal.valueOf(75), new SymbolNumber(new SpaceLexer("seventy five")).getValue());
        assertEquals("test 86", BigDecimal.valueOf(86), new SymbolNumber(new SpaceLexer("eighty six")).getValue());
        assertEquals("test 97", BigDecimal.valueOf(97), new SymbolNumber(new SpaceLexer("ninety seven")).getValue());

        assertEquals("test 100", BigDecimal.valueOf(100), new SymbolNumber(new SpaceLexer("one hundred")).getValue());
        assertEquals("test 1200", BigDecimal.valueOf(1200), new SymbolNumber(new SpaceLexer("twelve hundred")).getValue());
        assertEquals("test 2000", BigDecimal.valueOf(2000), new SymbolNumber(new SpaceLexer("twenty hundred")).getValue());
        assertEquals("test 3400", BigDecimal.valueOf(3400), new SymbolNumber(new SpaceLexer("thirty four hundred")).getValue());

        assertEquals("test 1000", BigDecimal.valueOf(1000), new SymbolNumber(new SpaceLexer("one thousand")).getValue());
        assertEquals("test 11000", BigDecimal.valueOf(11000), new SymbolNumber(new SpaceLexer("eleven thousand")).getValue());
        assertEquals("test 38000", BigDecimal.valueOf(38000), new SymbolNumber(new SpaceLexer("thirty eight thousand")).getValue());
        assertEquals("test 100000", BigDecimal.valueOf(100000), new SymbolNumber(new SpaceLexer("one hundred thousand")).getValue());
        assertEquals("test 101000", BigDecimal.valueOf(101000), new SymbolNumber(new SpaceLexer("one hundred one thousand")).getValue());
        assertEquals("test 212000", BigDecimal.valueOf(212000), new SymbolNumber(new SpaceLexer("two hundred twelve thousand")).getValue());
        assertEquals("test 360000", BigDecimal.valueOf(360000), new SymbolNumber(new SpaceLexer("three hundred sixty thousand")).getValue());
        assertEquals("test 491000", BigDecimal.valueOf(491000), new SymbolNumber(new SpaceLexer("four hundred ninety one thousand")).getValue());

        assertEquals("test 1001", BigDecimal.valueOf(1001), new SymbolNumber(new SpaceLexer("one thousand one")).getValue());
        assertEquals("test 20030", BigDecimal.valueOf(20030), new SymbolNumber(new SpaceLexer("twenty thousand thirty")).getValue());
        assertEquals("test 100045", BigDecimal.valueOf(100045), new SymbolNumber(new SpaceLexer("one hundred thousand forty five")).getValue());
        assertEquals("test 205100", BigDecimal.valueOf(205100), new SymbolNumber(new SpaceLexer("two hundred five thousand one hundred")).getValue());
        assertEquals("test 318208", BigDecimal.valueOf(318208), new SymbolNumber(new SpaceLexer("three hundred eighteen thousand two hundred eight")).getValue());
        assertEquals("test 490315", BigDecimal.valueOf(490315), new SymbolNumber(new SpaceLexer("four hundred ninety thousand three hundred fifteen")).getValue());
        assertEquals("test 500420", BigDecimal.valueOf(500420), new SymbolNumber(new SpaceLexer("five hundred thousand four hundred twenty")).getValue());
        assertEquals("test 600532", BigDecimal.valueOf(600532), new SymbolNumber(new SpaceLexer("six hundred thousand five hundred thirty two")).getValue());

        assertEquals("test 1,001,001", BigDecimal.valueOf(1001001), new SymbolNumber(new SpaceLexer("one million one thousand one")).getValue());
        assertEquals("test 16,012,013", BigDecimal.valueOf(16012013), new SymbolNumber(new SpaceLexer("sixteen million twelve thousand thirteen")).getValue());
        assertEquals("test 80,020,030", BigDecimal.valueOf(80020030), new SymbolNumber(new SpaceLexer("eighty million twenty thousand thirty")).getValue());
        assertEquals("test 100,100,045", BigDecimal.valueOf(100100045), new SymbolNumber(new SpaceLexer("one hundred million one hundred thousand forty five")).getValue());
        assertEquals("test 203,205,100", BigDecimal.valueOf(203205100), new SymbolNumber(new SpaceLexer("two hundred three million two hundred five thousand one hundred")).getValue());
        assertEquals("test 314,318,208", BigDecimal.valueOf(314318208), new SymbolNumber(new SpaceLexer("three hundred fourteen million three hundred eighteen thousand two hundred eight")).getValue());
        assertEquals("test 450,490,315", BigDecimal.valueOf(450490315), new SymbolNumber(new SpaceLexer("four hundred fifty million four hundred ninety thousand three hundred fifteen")).getValue());
        assertEquals("test 572,500,420", BigDecimal.valueOf(572500420), new SymbolNumber(new SpaceLexer("five hundred seventy two million five hundred thousand four hundred twenty")).getValue());

        assertEquals("test 1,314,318,208", BigDecimal.valueOf(1314318208), new SymbolNumber(new SpaceLexer("one billion three hundred fourteen million three hundred eighteen thousand two hundred eight")).getValue());
        assertEquals("test 2,450,490,315", new BigDecimal("2450490315"), new SymbolNumber(new SpaceLexer("two billion four hundred fifty million four hundred ninety thousand three hundred fifteen")).getValue());
        assertEquals("test 3,572,500,420", new BigDecimal("3572500420"), new SymbolNumber(new SpaceLexer("three billion five hundred seventy two million five hundred thousand four hundred twenty")).getValue());

        assertEquals("test 1001.20033", BigDecimal.valueOf(1001.20033), new SymbolNumber(new SpaceLexer("one thousand one dec twenty thousand thirty three")).getValue());
    }
}
