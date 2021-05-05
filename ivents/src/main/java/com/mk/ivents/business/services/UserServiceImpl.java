package com.mk.ivents.business.services;

import com.mk.ivents.business.dtos.EventDto;
import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.interfaces.EventService;
import com.mk.ivents.business.interfaces.UserService;
import com.mk.ivents.persistence.interfaces.EventRepository;
import com.mk.ivents.persistence.interfaces.UserRepository;
import com.mk.ivents.persistence.models.Event;
import com.mk.ivents.persistence.models.User;
import com.mk.ivents.persistence.models.UserProfile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final EventService eventService;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public UserServiceImpl(EventService eventService, UserRepository userRepository, EventRepository eventRepository) {
        this.eventService = eventService;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
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
    public User findByEmail(String email) throws NotFoundException {
        Optional<User> userOptional = userRepository.findByUserProfile_Email(email);

        return userOptional.orElseThrow(() -> new NotFoundException("Did not find user with email '"
                + email + "'"));
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

    @Override
    public boolean isUsernameAlreadyTaken(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        return userOptional.isPresent();
    }

    @Override
    public boolean isEventFavorite(int userId, int eventId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Did not find user with id '" + userId + "'"));

        return user
                .getFavoriteEvents()
                .stream()
                .anyMatch(event -> event.getId() == eventId);
    }

    @Override
    @Transactional
    public void addFavoriteEvent(int userId, int eventId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Did not find user with id '" + userId + "'"));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Did not find event with id '" + eventId + "'"));

        user.getFavoriteEvents().add(event);
    }

    @Override
    @Transactional
    public void removeFavoriteEvent(int userId, int eventId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Did not find user with id '" + userId + "'"));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Did not find event with id '" + eventId + "'"));

        user.getFavoriteEvents().remove(event);
    }

    @Override
    public boolean isInterestedInEvent(int userId, int eventId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Did not find user with id '" + userId + "'"));

        return user
                .getInterestedInEvents()
                .stream()
                .anyMatch(event -> event.getId() == eventId);
    }

    @Override
    @Transactional
    public void addInterestedInEvent(int userId, int eventId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Did not find user with id '" + userId + "'"));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Did not find event with id '" + eventId + "'"));

        user.removeGoingToEvent(event);
        user.addInterestedInEvent(event);
    }

    @Override
    @Transactional
    public void removeInterestedInEvent(int userId, int eventId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Did not find user with id '" + userId + "'"));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Did not find event with id '" + eventId + "'"));

        user.removeInterestedInEvent(event);
    }

    @Override
    public boolean isGoingToEvent(int userId, int eventId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Did not find user with id '" + userId + "'"));

        return user
                .getGoingToEvents()
                .stream()
                .anyMatch(event -> event.getId() == eventId);
    }

    @Override
    @Transactional
    public void addGoingToEvent(int userId, int eventId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Did not find user with id '" + userId + "'"));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Did not find event with id '" + eventId + "'"));

        user.removeInterestedInEvent(event);
        user.addGoingToEvent(event);
    }

    @Override
    @Transactional
    public void removeGoingToEvent(int userId, int eventId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Did not find user with id '" + userId + "'"));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Did not find event with id '" + eventId + "'"));

        user.removeGoingToEvent(event);
    }

    @Override
    public int getTotalNumberOfFavoritesPagesWithSize(int userId, int size) {
        return userRepository.getFavoriteEvents(userId,
                PageRequest.of(0, size)).getTotalPages();
    }

    @Override
    public List<EventDto> getFavoritesPage(int userId, int page, int size) {
        return eventService.convertToDto(userRepository
                .getFavoriteEvents(userId, PageRequest.of(page, size))
                .getContent());
    }

    @Override
    public int getTotalNumberOfInterestedInPagesWithSize(int userId, int size) {
        return userRepository.getInterestedInEvents(userId,
                PageRequest.of(0, size)).getTotalPages();
    }

    @Override
    public List<EventDto> getInterestedInPage(int userId, int page, int size) {
        return eventService.convertToDto(userRepository
                .getInterestedInEvents(userId, PageRequest.of(page, size))
                .getContent());
    }

    @Override
    public int getTotalNumberOfGoingToPagesWithSize(int userId, int size) {
        return userRepository.getGoingToEvents(userId,
                PageRequest.of(0, size)).getTotalPages();
    }

    @Override
    public List<EventDto> getGoingToPage(int userId, int page, int size) {
        return eventService.convertToDto(userRepository
                .getGoingToEvents(userId, PageRequest.of(page, size))
                .getContent());
    }
}
