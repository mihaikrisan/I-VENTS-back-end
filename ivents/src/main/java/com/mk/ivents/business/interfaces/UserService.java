package com.mk.ivents.business.interfaces;

import com.mk.ivents.business.dtos.AllTimeStats;
import com.mk.ivents.business.dtos.EventDto;
import com.mk.ivents.business.dtos.MonthlyStats;
import com.mk.ivents.business.dtos.UserDto;
import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.persistence.models.User;
import com.mk.ivents.persistence.models.UserProfile;

import java.util.List;

public interface UserService {
    List<User> findAll();

    UserDto findById(int id) throws NotFoundException;

    User findByUsername(String username) throws NotFoundException;

    User findByEmail(String email) throws NotFoundException;

    User save(User user);

    User update(User user, int userId) throws NotFoundException;

    void deleteById(int id);

    UserProfile getUserProfile(int userId) throws NotFoundException;

    boolean isUsernameAlreadyTaken(String username);

    boolean isEventFavorite(int userId, int eventId) throws NotFoundException;

    void addFavoriteEvent(int userId, int eventId) throws NotFoundException;

    void removeFavoriteEvent(int userId, int eventId) throws NotFoundException;

    boolean isInterestedInEvent(int userId, int eventId) throws NotFoundException;

    void addInterestedInEvent(int userId, int eventId) throws NotFoundException;

    void removeInterestedInEvent(int userId, int eventId) throws NotFoundException;

    boolean isGoingToEvent(int userId, int eventId) throws NotFoundException;

    void addGoingToEvent(int userId, int eventId) throws NotFoundException;

    void removeGoingToEvent(int userId, int eventId) throws NotFoundException;

    int getTotalNumberOfFavoritesPagesWithSize(int userId, int size);

    List<EventDto> getFavoritesPage(int userId, int page, int size);

    int getTotalNumberOfInterestedInPagesWithSize(int userId, int size);

    List<EventDto> getInterestedInPage(int userId, int page, int size);

    int getTotalNumberOfGoingToPagesWithSize(int userId, int size);

    List<EventDto> getGoingToPage(int userId, int page, int size);

    AllTimeStats getAllTimeStats(int userId);

    MonthlyStats getMonthlyStats(int userId);
}
