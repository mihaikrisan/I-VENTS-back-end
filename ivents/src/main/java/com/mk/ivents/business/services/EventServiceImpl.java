package com.mk.ivents.business.services;

import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.interfaces.EventService;
import com.mk.ivents.persistence.constants.EventCategory;
import com.mk.ivents.persistence.interfaces.EventRepository;
import com.mk.ivents.persistence.models.Event;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event findById(int id) throws NotFoundException {
        Optional<Event> eventOptional = eventRepository.findById(id);

        return eventOptional.orElseThrow(() -> new NotFoundException("Did not find event with id '" + id + "'"));
    }

    @Override
    @Transactional
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    @Transactional
    public Event update(Event event, int eventId) throws NotFoundException {
        findById(eventId);
        event.setId(eventId);

        return save(event);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> findAllEventsBelongingToCategory(String eventCategory) {
        return eventRepository.findByEventCategory(EventCategory.valueOf(eventCategory));
    }
}
