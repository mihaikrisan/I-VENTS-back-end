package com.mk.ivents.business.interfaces;

import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.persistence.models.Event;

import java.util.List;

public interface EventService {
    List<Event> findAll();

    Event findById(int id) throws NotFoundException;

    Event save(Event event);

    Event update(Event event, int eventId) throws NotFoundException;

    void deleteById(int id);

    List<Event> findAllEventsBelongingToCategory(String eventCategory);
}
