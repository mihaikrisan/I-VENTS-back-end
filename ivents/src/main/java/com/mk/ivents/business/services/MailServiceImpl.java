package com.mk.ivents.business.services;

import com.mk.ivents.business.exceptions.MailSendException;
import com.mk.ivents.business.interfaces.MailContentBuilderService;
import com.mk.ivents.business.interfaces.MailService;
import com.mk.ivents.business.models.ResetPasswordEmail;
import com.mk.ivents.business.models.VerificationEmail;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilderService mailContentBuilderService;

    public MailServiceImpl(JavaMailSender mailSender, MailContentBuilderService mailContentBuilderService) {
        this.mailSender = mailSender;
        this.mailContentBuilderService = mailContentBuilderService;
    }

    @Override
    @Async
    public void sendVerificationEmail(VerificationEmail verificationEmail) throws MailSendException {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("i-vents@email.com");
            mimeMessageHelper.setTo(verificationEmail.getRecipient());
            mimeMessageHelper.setSubject(verificationEmail.getSubject());
            mimeMessageHelper.setText(mailContentBuilderService
                    .buildVerificationEmail(verificationEmail.getTokenUuid()), true);
        };

        try {
            mailSender.send(mimeMessagePreparator);
        } catch (MailException mailException) {
            throw new MailSendException("Exception occurred when sending mail to '"
                    + verificationEmail.getRecipient() + "'");
        }
    }

    @Override
    @Async
    public void sendResetPasswordEmail(ResetPasswordEmail resetPasswordEmail) throws MailSendException {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("i-vents@email.com");
            mimeMessageHelper.setTo(resetPasswordEmail.getRecipient());
            mimeMessageHelper.setSubject(resetPasswordEmail.getSubject());
            mimeMessageHelper.setText(mailContentBuilderService
                    .buildResetPasswordEmail(resetPasswordEmail.getResetCode()), true);
        };

        try {
            mailSender.send(mimeMessagePreparator);
        } catch (MailException mailException) {
            throw new MailSendException("Exception occurred when sending mail to '"
                    + resetPasswordEmail.getRecipient() + "'");
        }
    }
}
