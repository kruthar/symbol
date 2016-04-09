package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class VariableDoesNotExistsException extends Exception {
    public VariableDoesNotExistsException() {}

    public VariableDoesNotExistsException(String message) {
        super(message);
    }

    public VariableDoesNotExistsException(Throwable cause) {
        super(cause);
    }

    public VariableDoesNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public VariableDoesNotExistsException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(message, cause, suppression, stacktrace);
    }
}
