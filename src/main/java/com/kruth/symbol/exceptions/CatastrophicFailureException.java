package com.kruth.symbol.exceptions;

/**
 * Created by kruthar on 4/9/16.
 */

/**
 * This Exception is for any type of internal error that cannot be controlled by the writer of the running Symbol Script.
 * This Exception represents a scenario that should be opened in a bug issue and looked into.
 */
public class CatastrophicFailureException extends SymbolException {
    public CatastrophicFailureException() {}

    public CatastrophicFailureException(String message) {
        super(getDisplayMessage(message));
    }

    public CatastrophicFailureException(Throwable cause) {
        super(cause);
    }

    public CatastrophicFailureException(String message, Throwable cause) {
        super(getDisplayMessage(message), cause);
    }

    public CatastrophicFailureException(String message, Throwable cause, Boolean suppression, Boolean stacktrace) {
        super(getDisplayMessage(message), cause, suppression, stacktrace);
    }
}
