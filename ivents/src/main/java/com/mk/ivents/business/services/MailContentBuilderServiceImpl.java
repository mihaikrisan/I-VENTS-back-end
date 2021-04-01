package com.mk.ivents.business.services;

import com.mk.ivents.business.interfaces.MailContentBuilderService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilderServiceImpl implements MailContentBuilderService {
    private final TemplateEngine templateEngine;

    public MailContentBuilderServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String buildVerificationEmail(String tokenUuid) {
        Context context = new Context();
        context.setVariable("tokenUuid", tokenUuid);
        return templateEngine.process("verificationemailtemplate", context);
    }

    @Override
    public String buildResetPasswordEmail(String resetCode) {
        Context context = new Context();
        context.setVariable("resetCode", resetCode);
        return templateEngine.process("resetpasswordmailtemplate", context);
    }
}
