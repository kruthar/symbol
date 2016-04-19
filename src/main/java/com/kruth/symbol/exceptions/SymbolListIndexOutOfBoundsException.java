package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class SymbolListIndexOutOfBoundsException extends SymbolException {
    public SymbolListIndexOutOfBoundsException() {}

    public SymbolListIndexOutOfBoundsException(String message) {
        super(getDisplayMessage(message));
    }

    public SymbolListIndexOutOfBoundsException(Throwable cause) {
        super(cause);
    }

    public SymbolListIndexOutOfBoundsException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public SymbolListIndexOutOfBoundsException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
