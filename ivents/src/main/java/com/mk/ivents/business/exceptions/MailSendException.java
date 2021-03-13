package com.mk.ivents.business.exceptions;

public class MailSendException extends RuntimeException {
    public MailSendException(String message) {
        super(message);
    }
}
