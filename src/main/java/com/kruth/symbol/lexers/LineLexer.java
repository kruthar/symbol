package com.kruth.symbol.lexers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * Created by kruthar on 2/24/16.
 */
public class LineLexer implements Iterator<String> {
    private List<String> lines;
    private int nextIndex;

    public LineLexer(File file) {
        nextIndex = 0;
        lines = new ArrayList<String>();

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
    }

    public LineLexer(String line) {
        nextIndex = 0;
        lines = new ArrayList<String>();

        lines.add(line);
    }

    public boolean hasNext() {
        return nextIndex < lines.size();
    }

    public String next() {
        return lines.get(nextIndex++);
    }

    public String peek() {
        return lines.get(nextIndex);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public int getIndex() {
        return nextIndex;
    }

    public void setIndex(int index) {
        nextIndex = index;
    }
}
