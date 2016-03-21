package com.kruth.symbol.comparators;

import com.kruth.symbol.lexers.SpaceLexer;
import com.kruth.symbol.operations.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kruthar on 3/19/16.
 */
public class ComparatorParser {
    private static final Map<String, Class> KEYWORDS;
    static {
        Map<String, Class> aMap = new HashMap<>();
        aMap.put("equals", Equals.class);
        aMap.put("notequals", NotEquals.class);
        KEYWORDS = Collections.unmodifiableMap(aMap);
    }

    public static Comparator parse(SpaceLexer lexer) {
        Comparator comp = null;
        try {
            comp = (Comparator) KEYWORDS.get(lexer.next()).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return comp;
    }

    public static boolean hasKeyword(String keyword) {
        return KEYWORDS.containsKey(keyword);
    }

}
