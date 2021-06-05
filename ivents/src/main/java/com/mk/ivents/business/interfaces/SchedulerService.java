package com.mk.ivents.business.interfaces;

import com.mk.ivents.persistence.models.Event;

import java.time.Instant;
import java.util.List;

public interface SchedulerService {
    List<Event> generateSchedule(List<Event> eventList, Instant endTimeUpperBound);
}
