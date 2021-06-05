package com.mk.ivents.rest.controllers;

import com.mk.ivents.business.dtos.AdvancedSearchRequest;
import com.mk.ivents.business.dtos.EventDto;
import com.mk.ivents.business.dtos.ScheduleRequest;
import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.interfaces.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventRestController {
    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
//    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<List<EventDto>> findAllEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> findEventById(@PathVariable int eventId) throws NotFoundException {
        return ResponseEntity.ok(eventService.findById(eventId));
    }

    @PostMapping
    public ResponseEntity<Void> addEvent(@RequestBody EventDto eventDto) throws NotFoundException {
        EventDto savedEventDto = eventService.save(eventDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEventDto.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@RequestBody EventDto eventDto, @PathVariable int eventId) throws NotFoundException {
        return ResponseEntity.ok(eventService.update(eventDto, eventId));
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable int eventId) {
        eventService.deleteById(eventId);
    }

    @GetMapping(params = "event_category")
    public ResponseEntity<List<EventDto>> getAllEventsBelongingToCategory(@RequestParam("event_category") String eventCategory) {
        return ResponseEntity.ok(eventService.findAllEventsBelongingToCategory(eventCategory));
    }

    @GetMapping(value = "/most-recent/number-of-pages", params = "size")
    public ResponseEntity<Integer> getTotalNumberOfMostRecentPagesWithSize(@RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getTotalNumberOfMostRecentPagesWithSize(size));
    }

    @GetMapping(value = "/most-recent", params = {"page", "size"})
    public ResponseEntity<List<EventDto>> getMostRecentPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getMostRecentPage(page, size));
    }

    @GetMapping("/{eventId}/interested-in/number-of-users")
    public ResponseEntity<Integer> getNumberOfUsersInterested(@PathVariable int eventId) throws NotFoundException {
        return ResponseEntity.ok(eventService.getNumberOfUsersInterested(eventId));
    }

    @GetMapping("/{eventId}/going-to/number-of-users")
    public ResponseEntity<Integer> getNumberOfUsersGoing(@PathVariable int eventId) throws NotFoundException {
        return ResponseEntity.ok(eventService.getNumberOfUsersGoing(eventId));
    }

    @GetMapping(value = "/popular/number-of-pages", params = "size")
    public ResponseEntity<Integer> getTotalNumberOfPopularPagesWithSize(@RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getTotalNumberOfPopularPagesWithSize(size));
    }

    @GetMapping(value = "/popular", params = {"page", "size"})
    public ResponseEntity<List<EventDto>> getPopularPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getPopularPage(page, size));
    }

    @GetMapping(value = "/recommended/{userId}/number-of-pages", params = "size")
    public ResponseEntity<Integer> getTotalNumberOfRecommendedPagesForUserWithSize(@PathVariable int userId,
                                                                                   @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getTotalNumberOfRecommendedPagesForUserWithSize(userId, size));
    }

    @GetMapping(value = "/recommended/{userId}", params = {"page", "size"})
    public ResponseEntity<List<EventDto>> getRecommendedPageForUser(@PathVariable int userId,
                                                                    @RequestParam("page") int page,
                                                                    @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getRecommendedPageForUser(userId, page, size));
    }

    @GetMapping(value = "/organizer/{userId}/number-of-pages", params = "size")
    public ResponseEntity<Integer> getTotalNumberOfAllOrganizerEventsPagesWithSize(@PathVariable int userId,
                                                                                   @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getTotalNumberOfAllOrganizerEventsPagesWithSize(userId, size));
    }

    @GetMapping(value = "/organizer/{userId}", params = {"page", "size"})
    public ResponseEntity<List<EventDto>> getAllOrganizerEventsPage(@PathVariable int userId,
                                                                    @RequestParam("page") int page,
                                                                    @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getAllOrganizerEventsPage(userId, page, size));
    }

    @GetMapping(value = "/search/{keyword}/number-of-pages", params = "size")
    public ResponseEntity<Integer> getTotalNumberOfFullTextSearchResultPagesWithSize(@PathVariable String keyword,
                                                                                     @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getTotalNumberOfFullTextSearchResultPagesWithSize(keyword, size));
    }

    @GetMapping(value = "/search/{keyword}", params = {"page", "size"})
    public ResponseEntity<List<EventDto>> getFullTextSearchPage(@PathVariable String keyword,
                                                                @RequestParam("page") int page,
                                                                @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getFullTextSearchPage(keyword, page, size));
    }

    @GetMapping(value = "/search/{keyword}/organizer/{userId}/number-of-pages", params = "size")
    public ResponseEntity<Integer> getTotalNumberOfOrganizerFullTextSearchResultPagesWithSize(@PathVariable String keyword,
                                                                                              @PathVariable int userId,
                                                                                              @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getTotalNumberOfOrganizerFullTextSearchResultPagesWithSize(keyword,
                userId, size));
    }

    @GetMapping(value = "/search/{keyword}/organizer/{userId}", params = {"page", "size"})
    public ResponseEntity<List<EventDto>> getOrganizerFullTextSearchPage(@PathVariable String keyword,
                                                                         @PathVariable int userId,
                                                                         @RequestParam("page") int page,
                                                                         @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getOrganizerFullTextSearchPage(keyword, userId, page, size));
    }

    @PostMapping(value = "/advanced-search", params = "size")
    public ResponseEntity<Integer> getTotalNumberOfAdvancedSearchResultPagesWithSize(@RequestBody AdvancedSearchRequest advancedSearchRequest,
                                                                                     @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getTotalNumberOfAdvancedSearchResultPagesWithSize(advancedSearchRequest,
                size));
    }

    @PostMapping(value = "/advanced-search", params = {"page", "size"})
    public ResponseEntity<List<EventDto>> getAdvancedSearchPage(@RequestBody AdvancedSearchRequest advancedSearchRequest,
                                                                @RequestParam("page") int page,
                                                                @RequestParam("size") int size) {
        return ResponseEntity.ok(eventService.getAdvancedSearchPage(advancedSearchRequest, page, size));
    }

    @PostMapping("/schedule")
    public ResponseEntity<List<EventDto>> getSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        return ResponseEntity.ok(eventService.getSchedule(scheduleRequest));
    }
}
