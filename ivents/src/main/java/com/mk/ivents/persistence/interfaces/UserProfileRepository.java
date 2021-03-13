package com.mk.ivents.persistence.interfaces;

import com.mk.ivents.persistence.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
}
