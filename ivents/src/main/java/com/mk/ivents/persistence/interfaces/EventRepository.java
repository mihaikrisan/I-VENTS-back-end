package com.mk.ivents.persistence.interfaces;

import com.mk.ivents.persistence.constants.EventCategory;
import com.mk.ivents.persistence.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByTakingPlaceTimeAfter(Instant pointInTime);

    List<Event> findByEventCategory(EventCategory eventCategory);

    Page<Event> findByTakingPlaceTimeAfter(Instant pointInTime, Pageable pageable);

    List<Event> findByUsersGoing_Id(int userId);

    List<Event> findByOrganizer_Id(int organizerId);

    List<Event> findByOrganizer_IdAndTakingPlaceTimeAfter(int organizerId, Instant pointInTime);

    Page<Event> getByOrganizer_IdAndTakingPlaceTimeAfter(int organizerId, Instant pointInTime, Pageable pageable);

    @Query(value = "SELECT * FROM event WHERE MATCH (event_title, event_category, description, address) " +
            "AGAINST (?1 IN BOOLEAN MODE)",
            countQuery = "SELECT COUNT(*) FROM event WHERE MATCH (event_title, event_category, description, address) " +
                    "AGAINST (?1 IN BOOLEAN MODE)",
            nativeQuery = true)
    Page<Event> fullTextSearch(String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM event WHERE MATCH (event_title, event_category, description, address) " +
            "AGAINST (?1 IN BOOLEAN MODE) AND organizer_id = ?2",
            countQuery = "SELECT COUNT(*) FROM event WHERE MATCH (event_title, event_category, description, address) " +
                    "AGAINST (?1 IN BOOLEAN MODE) AND organizer_id = ?2",
            nativeQuery = true)
    Page<Event> organizerFullTextSearch(String keyword, int organizerId, Pageable pageable);
}
