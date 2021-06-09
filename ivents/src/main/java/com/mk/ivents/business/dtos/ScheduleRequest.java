package com.mk.ivents.business.dtos;

import com.mk.ivents.persistence.constants.EventCategory;
import com.mk.ivents.persistence.models.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ScheduleRequest {
    private Instant scheduleDay;
    private List<EventCategory> preferredEventCategories;
    private List<String> preferredHashtags;
    private Position clientPosition;
}
