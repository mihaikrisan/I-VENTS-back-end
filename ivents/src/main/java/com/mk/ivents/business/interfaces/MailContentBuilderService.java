package com.mk.ivents.business.interfaces;

public interface MailContentBuilderService {
    String buildVerificationEmail(String tokenUuid);

    String buildResetPasswordEmail(String resetCode);
}
