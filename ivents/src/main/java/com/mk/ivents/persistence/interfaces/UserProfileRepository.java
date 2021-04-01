package com.mk.ivents.persistence.interfaces;

import com.mk.ivents.persistence.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    Optional<UserProfile> findByEmail(String email);

    Optional<UserProfile> findByPhoneNumber(String phoneNumber);
}
