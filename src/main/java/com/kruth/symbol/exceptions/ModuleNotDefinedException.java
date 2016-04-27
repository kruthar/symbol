package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class ModuleNotDefinedException extends SymbolException {
    public ModuleNotDefinedException() {}

    public ModuleNotDefinedException(String message) {
        super(getDisplayMessage(message));
    }

    public ModuleNotDefinedException(Throwable cause) {
        super(cause);
    }

    public ModuleNotDefinedException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public ModuleNotDefinedException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
