package com.code.bankapp.exception;

public class NoSuchException extends Exception {
    public NoSuchException() {
        super();
    }

    public NoSuchException(String message) {
        super(message);
    }

    public NoSuchException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchException(Throwable cause) {
        super(cause);
    }

    protected NoSuchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
