package com.mk.ivents.business.interfaces;

public interface DatabaseCleanerService {
    void cleanExpiredVerificationTokens();
}
