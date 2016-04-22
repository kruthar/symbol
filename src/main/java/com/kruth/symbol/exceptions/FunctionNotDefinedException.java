package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class FunctionNotDefinedException extends SymbolException {
    public FunctionNotDefinedException() {}

    public FunctionNotDefinedException(String message) {
        super(getDisplayMessage(message));
    }

    public FunctionNotDefinedException(Throwable cause) {
        super(cause);
    }

    public FunctionNotDefinedException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public FunctionNotDefinedException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
