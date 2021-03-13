package com.mk.ivents.persistence.interfaces;

import com.mk.ivents.persistence.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {
    Optional<VerificationToken> findByUuid(String uuid);
}
