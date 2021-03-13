package com.mk.ivents.business.interfaces;

import com.mk.ivents.business.exceptions.MailSendException;
import com.mk.ivents.business.models.VerificationEmail;

public interface MailService {
    void sendVerificationEmail(VerificationEmail verificationEmail) throws MailSendException;
}
