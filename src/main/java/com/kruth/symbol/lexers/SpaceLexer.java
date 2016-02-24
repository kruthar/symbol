package com.kruth.symbol.lexers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by kruthar on 2/24/16.
 */
public class SpaceLexer implements Iterator<String> {
    private String original;
    private List<String> words;
    private int nextIndex;

    /**
     * Takes a file name of a file in the resource file and parses it into a list of lines.
     * @param line
     */
    public SpaceLexer(String line) {
        original = line;
        nextIndex = 0;
        words = Arrays.asList(line.split(" "));
    }

    public boolean hasNext() {
        return nextIndex < words.size();
    }

    public String next() {
        return words.get(nextIndex++);
    }

    public String peek() {
        return words.get(nextIndex);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
