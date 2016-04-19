package com.kruth.symbol.exceptions;

import com.kruth.symbol.ErrorState;

/**
 * Created by kruthar on 4/18/16.
 */
public class SymbolException extends Exception {
    public SymbolException() {}

    public SymbolException(String message) {
        super(getDisplayMessage(message));
    }

    public SymbolException(Throwable cause) {
        super(cause);
    }

    public SymbolException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public SymbolException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }

    protected static String getDisplayMessage(String message) {
        return message + "(" + ErrorState.getFile() + ":" + ErrorState.getLine() + ")";
    }
}
