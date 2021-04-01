package com.mk.ivents.business.services;

import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.interfaces.UserProfileService;
import com.mk.ivents.business.interfaces.UserService;
import com.mk.ivents.persistence.interfaces.UserProfileRepository;
import com.mk.ivents.persistence.models.User;
import com.mk.ivents.persistence.models.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserService userService;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserService userService) {
        this.userProfileRepository = userProfileRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserProfile save(UserProfile user) {
        return userProfileRepository.save(user);
    }

    @Override
    @Transactional
    public UserProfile update(UserProfile userProfile, int userId) throws NotFoundException {
        User user = userService.findById(userId);

        int userProfileId = user.getUserProfile().getId();
        userProfile.setId(userProfileId);

        return save(userProfile);
    }

    @Override
    public boolean isEmailAlreadyTaken(String email) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByEmail(email);

        return userProfileOptional.isPresent();
    }

    @Override
    public boolean isPhoneNumberAlreadyTaken(String phoneNumber) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByPhoneNumber(phoneNumber);

        return userProfileOptional.isPresent();
    }
}
