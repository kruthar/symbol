package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class DotMethodNotDefinedException extends SymbolException {
    public DotMethodNotDefinedException() {}

    public DotMethodNotDefinedException(String message) {
        super(getDisplayMessage(message));
    }

    public DotMethodNotDefinedException(Throwable cause) {
        super(cause);
    }

    public DotMethodNotDefinedException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public DotMethodNotDefinedException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
