package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class UnexpectedStateException extends SymbolException {
    public UnexpectedStateException() {}

    public UnexpectedStateException(String message) {
        super(getDisplayMessage(message));
    }

    public UnexpectedStateException(Throwable cause) {
        super(cause);
    }

    public UnexpectedStateException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public UnexpectedStateException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
