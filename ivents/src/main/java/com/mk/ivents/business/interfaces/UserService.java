package com.mk.ivents.business.interfaces;

import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.persistence.models.User;
import com.mk.ivents.persistence.models.UserProfile;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id) throws NotFoundException;

    User findByUsername(String username) throws NotFoundException;

    User save(User user);

    User update(User user, int userId) throws NotFoundException;

    void deleteById(int id);

    UserProfile getUserProfile(int userId) throws NotFoundException;
}
