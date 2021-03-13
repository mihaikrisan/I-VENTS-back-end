package com.mk.ivents.business.exceptions;

public class InvalidVerificationTokenException extends Exception{
    public InvalidVerificationTokenException(String message) {
        super(message);
    }
}
