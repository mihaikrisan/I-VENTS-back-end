package com.mk.ivents.persistence.interfaces;

import com.mk.ivents.persistence.constants.EventCategory;
import com.mk.ivents.persistence.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByTakingPlaceTimeAfter(Instant pointInTime);

    List<Event> findByEventCategory(EventCategory eventCategory);

    Page<Event> findByTakingPlaceTimeAfter(Instant pointInTime, Pageable pageable);
}
