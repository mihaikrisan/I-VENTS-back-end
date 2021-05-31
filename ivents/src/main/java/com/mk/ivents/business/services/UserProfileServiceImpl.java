package com.mk.ivents.business.services;

import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.interfaces.UserProfileService;
import com.mk.ivents.persistence.interfaces.UserProfileRepository;
import com.mk.ivents.persistence.interfaces.UserRepository;
import com.mk.ivents.persistence.models.User;
import com.mk.ivents.persistence.models.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserProfile save(UserProfile user) {
        return userProfileRepository.save(user);
    }

    @Override
    @Transactional
    public UserProfile update(UserProfile userProfile, int userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Did not find user with id '"
                + userId + "'"));

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
