package com.omsu.chores_exchanger.models.exceptions;

public class UserAuthorityException extends ServiceException {
    public UserAuthorityException() {
    }

    public UserAuthorityException(String message) {
        super(message);
    }

    public UserAuthorityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAuthorityException(Throwable cause) {
        super(cause);
    }

    public UserAuthorityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
