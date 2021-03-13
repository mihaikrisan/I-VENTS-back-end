package com.mk.ivents.persistence.interfaces;

import com.mk.ivents.persistence.constants.EventCategory;
import com.mk.ivents.persistence.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByEventCategory(EventCategory eventCategory);
}
