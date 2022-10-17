package com.jungeeks.exception;

public class RegistrationFailedException extends RuntimeException {

    public RegistrationFailedException() {
        super();
    }

    public RegistrationFailedException(String message) {
        super(message);
    }

    public RegistrationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationFailedException(Throwable cause) {
        super(cause);
    }

    protected RegistrationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
