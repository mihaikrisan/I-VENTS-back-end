package com.mk.ivents.business.exceptions;

public class InvalidRefreshTokenException extends Exception {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
