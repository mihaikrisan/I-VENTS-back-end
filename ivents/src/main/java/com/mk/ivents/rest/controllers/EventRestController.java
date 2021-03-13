package com.mk.ivents.rest.controllers;

import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.interfaces.EventService;
import com.mk.ivents.persistence.models.Event;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public ResponseEntity<List<Event>> findAllEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> findEventById(@PathVariable int eventId) throws NotFoundException {
        return ResponseEntity.ok(eventService.findById(eventId));
    }

    @PostMapping
    public ResponseEntity<Object> addEvent(@RequestBody Event event) {
        Event savedEvent = eventService.save(event);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEvent.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @PathVariable int eventId) throws NotFoundException {
        return ResponseEntity.ok(eventService.update(event, eventId));
    }

    @GetMapping(params = "event-category")
    public ResponseEntity<List<Event>> getAllEventsBelongingToCategory(@RequestParam("event-category") String eventCategory) {
        return ResponseEntity.ok(eventService.findAllEventsBelongingToCategory(eventCategory));
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable int eventId) {
        eventService.deleteById(eventId);
    }
}
