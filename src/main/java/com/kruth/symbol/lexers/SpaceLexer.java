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
        if (line.isEmpty()) {
            words = new ArrayList<>();
        } else {
            words = Arrays.asList(line.split(" "));
        }
    }

    public boolean hasNext() {
        return nextIndex < words.size();
    }

    public String next() {
        return words.get(nextIndex++);
    }

    public String peek() {
        if (words.size() <= nextIndex) {
            return null;
        }
        return words.get(nextIndex);
    }

    public String peek(int offset) {
        if (words.size() <= nextIndex + offset) {
            return null;
        }
        return words.get(nextIndex + offset);
    }

    public String restOfLine() {
        String rest = "";

        while (this.hasNext()) {
            rest += this.next() + " ";
        }


        return rest.trim();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public String advancedTo(String stop) {
        String result = "";

        while (hasNext()) {
            String next = peek();

            if (next.equals(stop)) {
                break;
            }

            result += next() + " ";
        }

        return result.trim();
    }

    public String advancedToScoped(String start, String stop) {
        String result = "";
        int scope = 0;

        while (hasNext()) {
            String next = peek();

            if (next.equals(start)) {
                scope++;
            } else if (next.equals(stop)) {
                if (scope == 0) {
                    next();
                    break;
                } else {
                    scope--;
                }
            }

            result += next() + " ";
        }

        return result.trim();
    }
}
