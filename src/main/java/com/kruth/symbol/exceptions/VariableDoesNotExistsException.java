package com.kruth.symbol.exceptions;

import com.kruth.symbol.ErrorState;

/**
 * Created by kruthar on 4/9/16.
 */
public class VariableDoesNotExistsException extends Exception {
    public VariableDoesNotExistsException() {}

    public VariableDoesNotExistsException(String message) {
        super(getDisplayMessage(message));
    }

    public VariableDoesNotExistsException(Throwable cause) {
        super(cause);
    }

    public VariableDoesNotExistsException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public VariableDoesNotExistsException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }

    private static String getDisplayMessage(String message) {
        return message + "(" + ErrorState.getFile() + ":" + ErrorState.getLine() + ")";
    }
}
