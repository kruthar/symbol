package com.kruth.symbol;

/**
 * Created by kruthar on 4/11/16.
 */
public class ErrorState {
    private static ErrorState instance = null;

    private String currentFile = null;
    private int currentLine = 0;

    public static ErrorState getInstance() {
        if (instance == null) {
            instance = new ErrorState();
        }
        return instance;
    }

    public static void setFile(String file) {
        getInstance().currentFile = file;
    }

    public static String getFile() {
        return getInstance().currentFile;
    }

    public static void setLine(int line) {
        getInstance().currentLine = line;
    }

    public static int getLine() {
        return getInstance().currentLine;
    }

    public static void incrementLine() {
        getInstance().currentLine++;
    }

    public static void decrementLine(int dec) {
        getInstance().currentLine -= dec;
    }
}
