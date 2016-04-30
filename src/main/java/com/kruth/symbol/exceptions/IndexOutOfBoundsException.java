package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class IndexOutOfBoundsException extends SymbolException {
    public IndexOutOfBoundsException() {}

    public IndexOutOfBoundsException(String message) {
        super(getDisplayMessage(message));
    }

    public IndexOutOfBoundsException(Throwable cause) {
        super(cause);
    }

    public IndexOutOfBoundsException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public IndexOutOfBoundsException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
