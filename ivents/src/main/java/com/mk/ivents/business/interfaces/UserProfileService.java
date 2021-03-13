package com.mk.ivents.business.interfaces;

import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.persistence.models.UserProfile;

public interface UserProfileService {
    UserProfile save(UserProfile user);

    UserProfile update(UserProfile userProfile, int userId) throws NotFoundException;
}
