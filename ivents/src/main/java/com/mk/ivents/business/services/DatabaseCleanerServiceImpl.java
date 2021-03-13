package com.mk.ivents.business.services;

import com.mk.ivents.business.interfaces.DatabaseCleanerService;
import com.mk.ivents.persistence.interfaces.VerificationTokenRepository;
import com.mk.ivents.persistence.models.VerificationToken;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class DatabaseCleanerServiceImpl implements DatabaseCleanerService {
    private final VerificationTokenRepository verificationTokenRepository;

    public DatabaseCleanerServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    @Scheduled(fixedRate = 86400000, initialDelay = 86400000)
    public void cleanExpiredVerificationTokens() {
        List<VerificationToken> verificationTokens = verificationTokenRepository.findAll();
        Instant cronJobStartTime = Instant.now();

        for (VerificationToken verificationToken : verificationTokens) {
            if (cronJobStartTime.isAfter(verificationToken.getExpiryDate())) {
                verificationTokenRepository.delete(verificationToken);
            }
        }
    }
}
