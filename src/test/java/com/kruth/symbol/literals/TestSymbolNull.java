package com.kruth.symbol.literals;

import com.kruth.symbol.exceptions.SymbolException;
import com.kruth.symbol.lexers.SpaceLexer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by kruthar on 4/29/16.
 */
public class TestSymbolNull {
    @Test
    public void testConstructors() throws SymbolException {
        SymbolNull null1 = new SymbolNull(new SpaceLexer("null"));
        assertEquals("Null constructor", null, null1.getValue());
    }
}
