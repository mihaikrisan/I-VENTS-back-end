package com.mk.ivents.business.services;

import com.mk.ivents.business.dtos.AdvancedSearchRequest;
import com.mk.ivents.business.dtos.EventDto;
import com.mk.ivents.business.dtos.ScheduleRequest;
import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.interfaces.EventService;
import com.mk.ivents.business.interfaces.SchedulerService;
import com.mk.ivents.persistence.constants.EventCategory;
import com.mk.ivents.persistence.interfaces.EventRepository;
import com.mk.ivents.persistence.interfaces.UserRepository;
import com.mk.ivents.persistence.models.Event;
import com.mk.ivents.persistence.models.Position;
import com.mk.ivents.persistence.models.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SchedulerService schedulerService;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository, ModelMapper modelMapper,
                            SchedulerService schedulerService) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.schedulerService = schedulerService;
    }

    @Override
    public List<EventDto> findAll() {
        return convertToDto(eventRepository.findByTakingPlaceTimeAfter(Instant.now()));
    }

    @Override
    public EventDto findById(int id) throws NotFoundException {
        Optional<Event> eventOptional = eventRepository.findById(id);

        return convertToDto(eventOptional
                .orElseThrow(() -> new NotFoundException("Did not find event with id '" + id + "'")));
    }

    @Override
    @Transactional
    public EventDto save(EventDto eventDto) throws NotFoundException {
        Event event = convertToEntity(eventDto);
        event.setAddedTime(Instant.now());

        Optional<User> organizer = userRepository.findById(eventDto.getOrganizerId());
        event.setOrganizer(organizer.orElseThrow(() ->
                new NotFoundException("Did not find organizer with id '" + eventDto.getOrganizerId() + "'")));

        return convertToDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventDto update(EventDto eventDto, int eventId) throws NotFoundException {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException("Did not find event with id '" + eventId + "'"));
        eventDto.setId(eventId);
        mergeDtoWithEntity(eventDto, event);

        return convertToDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<EventDto> findAllEventsBelongingToCategory(String eventCategory) {
        return convertToDto(eventRepository.findByEventCategory(EventCategory.valueOf(eventCategory)));
    }

    @Override
    public int getTotalNumberOfMostRecentPagesWithSize(int size) {
        return eventRepository.findByTakingPlaceTimeAfter(Instant.now(),
                PageRequest.of(0, size, Sort.by("addedTime").descending())).getTotalPages();
    }

    @Override
    public List<EventDto> getMostRecentPage(int page, int size) {
        return convertToDto(eventRepository
                .findByTakingPlaceTimeAfter(Instant.now(), PageRequest.of(page, size, Sort.by("addedTime").descending()))
                .getContent());
    }

    @Override
    public int getNumberOfUsersInterested(int id) throws NotFoundException {
        return eventRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Did not find event with id '" + id + "'"))
                .getUsersInterested().size();
    }

    @Override
    public int getNumberOfUsersGoing(int id) throws NotFoundException {
        return eventRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Did not find event with id '" + id + "'"))
                .getUsersGoing().size();
    }

    @Override
    public int getTotalNumberOfPopularPagesWithSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }

        return (int) Math.ceil(eventRepository.findByTakingPlaceTimeAfter(Instant.now()).size() / (double) size);
    }

    @Override
    public List<EventDto> getPopularPage(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException();
        }

        List<EventDto> rankedEventsList = convertToDto(rankEvents());
        int fromIndex = page * size;

        if (rankedEventsList.size() < fromIndex) {
            return Collections.emptyList();
        }

        return rankedEventsList.subList(fromIndex, Math.min(fromIndex + size, rankedEventsList.size()));
    }

    @Override
    public int getTotalNumberOfRecommendedPagesForUserWithSize(int userId, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }

        return (int) Math.ceil(getRecommendedEventsForUser(userId).size() / (double) size);
    }

    @Override
    public List<EventDto> getRecommendedPageForUser(int userId, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException();
        }

        List<EventDto> recommendedEventsForUser = convertToDto(getRecommendedEventsForUser(userId));
        int fromIndex = page * size;

        if (recommendedEventsForUser.size() < fromIndex) {
            return Collections.emptyList();
        }

        return recommendedEventsForUser.subList(fromIndex, Math.min(fromIndex + size, recommendedEventsForUser.size()));
    }

    @Override
    public int getTotalNumberOfAllOrganizerEventsPagesWithSize(int userId, int size) {
        return eventRepository.getByOrganizer_IdAndTakingPlaceTimeAfter(userId, Instant.now(),
                PageRequest.of(0, size, Sort.by("addedTime").descending())).getTotalPages();
    }

    @Override
    public List<EventDto> getAllOrganizerEventsPage(int userId, int page, int size) {
        return convertToDto(eventRepository
                .getByOrganizer_IdAndTakingPlaceTimeAfter(userId, Instant.now(),
                        PageRequest.of(page, size, Sort.by("addedTime").descending()))
                .getContent());
    }

    @Override
    public int getTotalNumberOfFullTextSearchResultPagesWithSize(String keyword, int size) {
        return eventRepository.fullTextSearch(keyword + "*",
                PageRequest.of(0, size)).getTotalPages();
    }

    @Override
    public List<EventDto> getFullTextSearchPage(String keyword, int page, int size) {
        return convertToDto(eventRepository
                .fullTextSearch(keyword + "*", PageRequest.of(page, size))
                .getContent());
    }

    @Override
    public int getTotalNumberOfOrganizerFullTextSearchResultPagesWithSize(String keyword, int organizerId, int size) {
        return eventRepository.organizerFullTextSearch(keyword + "*", organizerId,
                PageRequest.of(0, size)).getTotalPages();
    }

    @Override
    public List<EventDto> getOrganizerFullTextSearchPage(String keyword, int organizerId, int page, int size) {
        return convertToDto(eventRepository
                .organizerFullTextSearch(keyword + "*", organizerId, PageRequest.of(page, size))
                .getContent());
    }

    @Override
    public int getTotalNumberOfAdvancedSearchResultPagesWithSize(AdvancedSearchRequest advancedSearchRequest, int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }

        List<Event> searchResults = eventRepository.advancedSearch(advancedSearchRequest.getTitle(),
                advancedSearchRequest.getEventCategory(), advancedSearchRequest.getTakingPlaceStart(),
                advancedSearchRequest.getTakingPlaceEnd());

        if (advancedSearchRequest.getHashtags() != null) {
            searchResults = searchResults
                    .stream()
                    .filter(event -> event.getHashtags().stream().anyMatch(advancedSearchRequest.getHashtags()::contains))
                    .collect(Collectors.toList());
        }

        return (int) Math.ceil(searchResults.size() / (double) size);
    }

    @Override
    public List<EventDto> getAdvancedSearchPage(AdvancedSearchRequest advancedSearchRequest, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException();
        }

        List<EventDto> searchResults = convertToDto(eventRepository.advancedSearch(advancedSearchRequest.getTitle(),
                advancedSearchRequest.getEventCategory(), advancedSearchRequest.getTakingPlaceStart(),
                advancedSearchRequest.getTakingPlaceEnd()));

        if (advancedSearchRequest.getHashtags() != null) {
            searchResults = searchResults
                    .stream()
                    .filter(event -> event.getHashtags().stream().anyMatch(advancedSearchRequest.getHashtags()::contains))
                    .collect(Collectors.toList());
        }

        int fromIndex = page * size;

        if (searchResults.size() < fromIndex) {
            return Collections.emptyList();
        }

        return searchResults.subList(fromIndex, Math.min(fromIndex + size, searchResults.size()));
    }

    @Override
    public List<EventDto> getSchedule(ScheduleRequest scheduleRequest) {
        Instant endTime = scheduleRequest.getScheduleDay().plus(Duration.ofDays(1));
        List<Event> eventList = eventRepository.findByTakingPlaceTimeBetween(scheduleRequest.getScheduleDay(),
                endTime);

        eventList = eventList
                .stream()
                .filter(event -> haversine(scheduleRequest.getClientPosition(), event.getPosition()) <= 5)
                .collect(Collectors.toList());

        if (!scheduleRequest.getPreferredEventCategories().isEmpty()) {
            eventList = eventList
                    .stream()
                    .filter(event -> scheduleRequest.getPreferredEventCategories().contains(event.getEventCategory()))
                    .collect(Collectors.toList());
        }

        if (!scheduleRequest.getPreferredHashtags().isEmpty()) {
            eventList = eventList
                    .stream()
                    .filter(event -> event.getHashtags().stream().anyMatch(hashtag -> scheduleRequest.getPreferredHashtags().contains(hashtag)))
                    .collect(Collectors.toList());
        }

        return convertToDto(schedulerService.generateSchedule(eventList, endTime));
    }

    @Override
    public EventDto convertToDto(Event event) {
        return modelMapper.map(event, EventDto.class);
    }

    @Override
    public List<EventDto> convertToDto(List<Event> eventList) {
        return modelMapper.map(eventList, new TypeToken<List<EventDto>>() {
        }.getType());
    }

    @Override
    public Event convertToEntity(EventDto eventDto) {
        return modelMapper.map(eventDto, Event.class);
    }

    private void mergeDtoWithEntity(EventDto eventDto, Event event) {
        modelMapper.map(eventDto, event);
    }

    private List<Event> rankEvents() {
        Instant rankingStartTime = Instant.now();

        return eventRepository
                .findByTakingPlaceTimeAfter(Instant.now())
                .stream()
                .sorted(Comparator.comparing((Event event) -> (event.getUsersGoing().size() +
                        event.getUsersInterested().size() * 0.25 + 10) / (1 +
                        Math.pow(Duration.between(rankingStartTime, event.getTakingPlaceTime()).toHours(), 0.6)))
                        .reversed())
                .collect(Collectors.toList());
    }

    private List<Event> getRecommendedEventsForUser(int userId) {
        List<Event> goingToEvents = eventRepository.findByUsersGoing_Id(userId);

        if (goingToEvents.size() == 0) {
            return rankEvents();
        }

        List<EventCategory> categoriesOfEventsToWhichTheUserIsGoing = goingToEvents
                .stream()
                .map(Event::getEventCategory)
                .distinct()
                .collect(Collectors.toList());

        return rankEvents()
                .stream()
                .filter(event -> categoriesOfEventsToWhichTheUserIsGoing.contains(event.getEventCategory()))
                .collect(Collectors.toList());
    }

    private double haversine(Position position1, Position position2) {
        double earthsRadiusInKm = 6371;

        double deltaLat = Math.toRadians(position2.getLat() - position1.getLat());
        double deltaLng = Math.toRadians(position2.getLng() - position1.getLng());

        double lat1 = Math.toRadians(position1.getLat());
        double lat2 = Math.toRadians(position2.getLat());

        return earthsRadiusInKm * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.pow(Math.sin(deltaLng / 2), 2) * Math.cos(lat1) * Math.cos(lat2)));
    }
}
