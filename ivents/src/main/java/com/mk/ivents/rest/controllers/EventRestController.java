package com.mk.ivents.rest.controllers;

import com.mk.ivents.business.dtos.EventDto;
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
    public ResponseEntity<Void> addEvent(@RequestBody EventDto eventDto) {
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
}
