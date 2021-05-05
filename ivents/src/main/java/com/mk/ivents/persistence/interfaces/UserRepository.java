package com.mk.ivents.persistence.interfaces;

import com.mk.ivents.persistence.models.Event;
import com.mk.ivents.persistence.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUserProfile_Email(String email);

    @Query("SELECT favoriteEvents FROM User u INNER JOIN u.favoriteEvents favoriteEvents WHERE u.id = :userId")
    Page<Event> getFavoriteEvents(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT interestedInEvents FROM User u INNER JOIN u.interestedInEvents interestedInEvents WHERE u.id = :userId")
    Page<Event> getInterestedInEvents(@Param("userId") int userId, Pageable pageable);

    @Query("SELECT goingToEvents FROM User u INNER JOIN u.goingToEvents goingToEvents WHERE u.id = :userId")
    Page<Event> getGoingToEvents(@Param("userId") int userId, Pageable pageable);
}
