package com.kruth.symbol.operations;

import com.kruth.symbol.lexers.SpaceLexer;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kruthar on 2/25/16.
 */
public class OperationParser {
    private static final Map<String, Class> KEYWORDS;
    static {
        Map<String, Class> aMap = new HashMap<>();
        aMap.put("times", Times.class);
        aMap.put("dividedby", DividedBy.class);
        aMap.put("plus", Plus.class);
        aMap.put("minus", Minus.class);
        aMap.put("modulo", Modulo.class);
        aMap.put("or", Or.class);
        aMap.put("and", And.class);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    public static Operation parse(SpaceLexer lexer) {
        Operation op = null;
        try {
            op = (Operation) KEYWORDS.get(lexer.next()).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return op;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }

}
