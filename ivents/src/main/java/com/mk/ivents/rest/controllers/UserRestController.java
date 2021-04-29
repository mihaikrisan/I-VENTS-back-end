package com.mk.ivents.rest.controllers;

import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.interfaces.UserProfileService;
import com.mk.ivents.business.interfaces.UserService;
import com.mk.ivents.persistence.models.User;
import com.mk.ivents.persistence.models.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {
    private final UserService userService;
    private final UserProfileService userProfileService;

    public UserRestController(UserService userService, UserProfileService userProfileService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserById(@PathVariable int userId) throws NotFoundException {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable int userId) throws NotFoundException {
        return ResponseEntity.ok(userService.update(user, userId));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteById(userId);
    }

    @GetMapping("/{userId}/userProfile")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable int userId) throws NotFoundException {
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PutMapping("/{userId}/userProfile")
    public ResponseEntity<UserProfile> updateUserProfile(@RequestBody UserProfile userProfile, @PathVariable int userId)
            throws NotFoundException {
        return ResponseEntity.ok(userProfileService.update(userProfile, userId));
    }

    @GetMapping("/{userId}/favorite-events/{eventId}")
    public ResponseEntity<Void> isEventFavorite(@PathVariable int userId, @PathVariable int eventId) throws NotFoundException {
        if (userService.isEventFavorite(userId, eventId)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

//    @PutMapping("/{userId}/favorite-events/{eventId}")
//    public void addFavoriteEvent(@PathVariable int userId, @PathVariable int eventId) throws NotFoundException {
//        userService.addFavoriteEvent(userId, eventId);
//    }

//    @DeleteMapping("/{userId}/favorite-events/{eventId}")
//    public void deleteFavoriteEvent(@PathVariable int userId, @PathVariable int eventId) throws NotFoundException {
//        userService.deleteFavoriteEvent(userId, eventId);
//    }
}
