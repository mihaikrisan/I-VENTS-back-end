package com.mk.ivents.business.interfaces;

import com.mk.ivents.business.dtos.EventDto;
import com.mk.ivents.business.exceptions.NotFoundException;
import com.mk.ivents.persistence.models.Event;

import java.util.List;

public interface EventService {
    List<EventDto> findAll();

    EventDto findById(int id) throws NotFoundException;

    EventDto save(EventDto eventDto) throws NotFoundException;

    EventDto update(EventDto eventDto, int eventId) throws NotFoundException;

    void deleteById(int id);

    List<EventDto> findAllEventsBelongingToCategory(String eventCategory);

    int getTotalNumberOfMostRecentPagesWithSize(int size);

    List<EventDto> getMostRecentPage(int page, int size);

    int getNumberOfUsersInterested(int id) throws NotFoundException;

    int getNumberOfUsersGoing(int id) throws NotFoundException;

    EventDto convertToDto(Event event);

    List<EventDto> convertToDto(List<Event> eventList);

    Event convertToEntity(EventDto eventDto);

    int getTotalNumberOfPopularPagesWithSize(int size);

    List<EventDto> getPopularPage(int page, int size);

    int getTotalNumberOfRecommendedPagesForUserWithSize(int userId, int size);

    List<EventDto> getRecommendedPageForUser(int userId, int page, int size);

    int getTotalNumberOfAllOrganizerEventsPagesWithSize(int userId, int size);

    List<EventDto> getAllOrganizerEventsPage(int userId, int page, int size);
}
