package com.mk.ivents.business.services;

import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.interfaces.UserService;
import com.mk.ivents.persistence.interfaces.UserRepository;
import com.mk.ivents.persistence.models.User;
import com.mk.ivents.persistence.models.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(id);

        return userOptional.orElseThrow(() -> new NotFoundException("Did not find user with id '" + id + "'"));
    }

    @Override
    public User findByUsername(String username) throws NotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        return userOptional.orElseThrow(() -> new NotFoundException("Did not find user with username '"
                + username + "'"));
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user, int userId) throws NotFoundException {
        findById(userId);
        user.setId(userId);

        return save(user);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserProfile getUserProfile(int userId) throws NotFoundException {
        return findById(userId).getUserProfile();
    }
}
