package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */
public class UnexpectedKeywordException extends SymbolException {
    public UnexpectedKeywordException() {}

    public UnexpectedKeywordException(String message) {
        super(getDisplayMessage(message));
    }

    public UnexpectedKeywordException(Throwable cause) {
        super(cause);
    }

    public UnexpectedKeywordException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public UnexpectedKeywordException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
