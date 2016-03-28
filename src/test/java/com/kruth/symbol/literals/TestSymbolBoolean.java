package com.kruth.symbol.literals;

import com.kruth.symbol.lexers.SpaceLexer;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 3/27/16.
 */
public class TestSymbolBoolean {
    @Test
    public  void testConstructors() {
        SymbolBoolean bool1 = new SymbolBoolean(false);
        assertEquals(false, bool1.getValue());

        SymbolBoolean bool2 = new SymbolBoolean(true);
        assertEquals(true, bool2.getValue());

        SymbolBoolean bool3 = new SymbolBoolean(new SpaceLexer("false"));
        assertEquals(false, bool3.getValue());

        SymbolBoolean bool4 = new SymbolBoolean(new SpaceLexer("true"));
        assertEquals(true, bool4.getValue());

        SymbolBoolean bool5 = new SymbolBoolean(new SpaceLexer("not false"));
        assertEquals(true, bool5.getValue());

        SymbolBoolean bool6 = new SymbolBoolean(new SpaceLexer("not true"));
        assertEquals(false, bool6.getValue());
    }
}
