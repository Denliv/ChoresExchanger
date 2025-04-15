package com.omsu.chores_exchanger.models.exceptions;

public class ChoreNotFoundException extends ServiceException {
    public ChoreNotFoundException() {
    }

    public ChoreNotFoundException(String message) {
        super(message);
    }

    public ChoreNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChoreNotFoundException(Throwable cause) {
        super(cause);
    }

    public ChoreNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
