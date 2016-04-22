package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class UnknownInstructionException extends SymbolException {
    public UnknownInstructionException() {}

    public UnknownInstructionException(String message) {
        super(getDisplayMessage(message));
    }

    public UnknownInstructionException(Throwable cause) {
        super(cause);
    }

    public UnknownInstructionException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public UnknownInstructionException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
