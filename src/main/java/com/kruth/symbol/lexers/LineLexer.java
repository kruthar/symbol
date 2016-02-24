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

    /**
     * Takes a file name of a file in the resource file and parses it into a list of lines.
     * @param filename
     */
    public LineLexer(String filename) {
        nextIndex = 0;
        lines = new ArrayList<String>();

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(getClass().getClassLoader().getResource(filename).getFile()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
    }

    public boolean hasNext() {
        return nextIndex < lines.size();
    }

    public String next() {
        return lines.get(nextIndex++);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
