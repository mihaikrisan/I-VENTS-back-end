package com.mk.ivents.business.services;

import com.mk.ivents.business.dtos.EventDto;
import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.business.interfaces.EventService;
import com.mk.ivents.persistence.constants.EventCategory;
import com.mk.ivents.persistence.interfaces.EventRepository;
import com.mk.ivents.persistence.models.Event;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;

    public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<EventDto> findAll() {
        return convertToDto(eventRepository.findAll());
    }

    @Override
    public EventDto findById(int id) throws NotFoundException {
        Optional<Event> eventOptional = eventRepository.findById(id);

        return convertToDto(eventOptional
                .orElseThrow(() -> new NotFoundException("Did not find event with id '" + id + "'")));
    }

    @Override
    @Transactional
    public EventDto save(EventDto eventDto) {
        Event event = convertToEntity(eventDto);
        event.setAddedTime(Instant.now());

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

    private EventDto convertToDto(Event event) {
        return modelMapper.map(event, EventDto.class);
    }

    private List<EventDto> convertToDto(List<Event> eventList) {
        return modelMapper.map(eventList, new TypeToken<List<EventDto>>() {
        }.getType());
    }

    private Event convertToEntity(EventDto eventDto) {
        return modelMapper.map(eventDto, Event.class);
    }

    private void mergeDtoWithEntity(EventDto eventDto, Event event) {
        modelMapper.map(eventDto, event);
    }
}
