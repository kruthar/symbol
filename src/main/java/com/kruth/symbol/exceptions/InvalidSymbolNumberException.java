package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class InvalidSymbolNumberException extends SymbolException {
    public InvalidSymbolNumberException() {}

    public InvalidSymbolNumberException(String message) {
        super(getDisplayMessage(message));
    }

    public InvalidSymbolNumberException(Throwable cause) {
        super(cause);
    }

    public InvalidSymbolNumberException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public InvalidSymbolNumberException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
