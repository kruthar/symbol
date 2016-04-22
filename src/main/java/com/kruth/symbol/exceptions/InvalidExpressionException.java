package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class InvalidExpressionException extends SymbolException {
    public InvalidExpressionException() {}

    public InvalidExpressionException(String message) {
        super(getDisplayMessage(message));
    }

    public InvalidExpressionException(Throwable cause) {
        super(cause);
    }

    public InvalidExpressionException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public InvalidExpressionException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
