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
public class EventDto {
    private int id;
    private String title;
    private EventCategory eventCategory;
    private List<String> hashtags;
    private Integer minimumAge;
    private Integer maxNumberOfPersons;
    private Position position;
    private Instant takingPlaceTime;
    private String imageUrl;
    private String address;
    private String description;
}
