package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class FailedToParseModuleException extends SymbolException {
    public FailedToParseModuleException() {}

    public FailedToParseModuleException(String message) {
        super(getDisplayMessage(message));
    }

    public FailedToParseModuleException(Throwable cause) {
        super(cause);
    }

    public FailedToParseModuleException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public FailedToParseModuleException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
