package com.mk.ivents.business.interfaces;

import com.mk.ivents.business.dtos.EventDto;
import com.mk.ivents.business.exceptions.NotFoundException;

import java.util.List;

public interface EventService {
    List<EventDto> findAll();

    EventDto findById(int id) throws NotFoundException;

    EventDto save(EventDto eventDto);

    EventDto update(EventDto eventDto, int eventId) throws NotFoundException;

    void deleteById(int id);

    List<EventDto> findAllEventsBelongingToCategory(String eventCategory);

    int getTotalNumberOfMostRecentPagesWithSize(int size);

    List<EventDto> getMostRecentPage(int page, int size);
}
