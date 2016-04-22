package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class FunctionAlreadyDefinedException extends SymbolException {
    public FunctionAlreadyDefinedException() {}

    public FunctionAlreadyDefinedException(String message) {
        super(getDisplayMessage(message));
    }

    public FunctionAlreadyDefinedException(Throwable cause) {
        super(cause);
    }

    public FunctionAlreadyDefinedException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public FunctionAlreadyDefinedException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
