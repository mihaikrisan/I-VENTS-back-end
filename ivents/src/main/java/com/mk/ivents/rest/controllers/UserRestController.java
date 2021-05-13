package com.mk.ivents.rest.controllers;

import com.mk.ivents.business.dtos.AllTimeStats;
import com.mk.ivents.business.dtos.EventDto;
import com.mk.ivents.business.dtos.MonthlyStats;
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

    @PutMapping("/{userId}/favorite-events/{eventId}")
    public void addFavoriteEvent(@PathVariable int userId, @PathVariable int eventId) throws NotFoundException {
        userService.addFavoriteEvent(userId, eventId);
    }

    @DeleteMapping("/{userId}/favorite-events/{eventId}")
    public void removeFavoriteEvent(@PathVariable int userId, @PathVariable int eventId) throws NotFoundException {
        userService.removeFavoriteEvent(userId, eventId);
    }

    @GetMapping("/{userId}/interested-in/{eventId}")
    public ResponseEntity<Void> isInterestedInEvent(@PathVariable int userId, @PathVariable int eventId) throws NotFoundException {
        if (userService.isInterestedInEvent(userId, eventId)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}/interested-in/{eventId}")
    public void addInterestedInEvent(@PathVariable int userId, @PathVariable int eventId) throws NotFoundException {
        userService.addInterestedInEvent(userId, eventId);
    }

    @DeleteMapping("/{userId}/interested-in/{eventId}")
    public void removeInterestedInEvent(@PathVariable int userId, @PathVariable int eventId) throws NotFoundException {
        userService.removeInterestedInEvent(userId, eventId);
    }

    @GetMapping("/{userId}/going-to/{eventId}")
    public ResponseEntity<Void> isGoingToEvent(@PathVariable int userId, @PathVariable int eventId) throws NotFoundException {
        if (userService.isGoingToEvent(userId, eventId)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}/going-to/{eventId}")
    public void addGoingToEvent(@PathVariable int userId, @PathVariable int eventId) throws NotFoundException {
        userService.addGoingToEvent(userId, eventId);
    }

    @DeleteMapping("/{userId}/going-to/{eventId}")
    public void removeGoingToEvent(@PathVariable int userId, @PathVariable int eventId) throws NotFoundException {
        userService.removeGoingToEvent(userId, eventId);
    }

    @GetMapping(value = "/{userId}/favorites/number-of-pages", params = "size")
    public ResponseEntity<Integer> getTotalNumberOfFavoritesPagesWithSize(@PathVariable int userId,
                                                                          @RequestParam("size") int size) {
        return ResponseEntity.ok(userService.getTotalNumberOfFavoritesPagesWithSize(userId, size));
    }

    @GetMapping(value = "/{userId}/favorites", params = {"page", "size"})
    public ResponseEntity<List<EventDto>> getFavoritesPage(@PathVariable int userId, @RequestParam("page") int page,
                                                           @RequestParam("size") int size) {
        return ResponseEntity.ok(userService.getFavoritesPage(userId, page, size));
    }

    @GetMapping(value = "/{userId}/interested-in/number-of-pages", params = "size")
    public ResponseEntity<Integer> getTotalNumberOfInterestedOnPagesWithSize(@PathVariable int userId,
                                                                             @RequestParam("size") int size) {
        return ResponseEntity.ok(userService.getTotalNumberOfInterestedInPagesWithSize(userId, size));
    }

    @GetMapping(value = "/{userId}/interested-in", params = {"page", "size"})
    public ResponseEntity<List<EventDto>> getInterestedInPage(@PathVariable int userId, @RequestParam("page") int page,
                                                              @RequestParam("size") int size) {
        return ResponseEntity.ok(userService.getInterestedInPage(userId, page, size));
    }

    @GetMapping(value = "/{userId}/going-to/number-of-pages", params = "size")
    public ResponseEntity<Integer> getTotalNumberOfGoingToPagesWithSize(@PathVariable int userId,
                                                                        @RequestParam("size") int size) {
        return ResponseEntity.ok(userService.getTotalNumberOfGoingToPagesWithSize(userId, size));
    }

    @GetMapping(value = "/{userId}/going-to", params = {"page", "size"})
    public ResponseEntity<List<EventDto>> getGoingToPage(@PathVariable int userId, @RequestParam("page") int page,
                                                         @RequestParam("size") int size) {
        return ResponseEntity.ok(userService.getGoingToPage(userId, page, size));
    }

    @GetMapping("/{userId}/all-time-stats")
    public ResponseEntity<AllTimeStats> getAllTimeStats(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getAllTimeStats(userId));
    }

    @GetMapping("/{userId}/monthly-stats")
    public ResponseEntity<MonthlyStats> getMonthlyStats(@PathVariable int userId) {
        return ResponseEntity.ok(userService.getMonthlyStats(userId));
    }
}
