package com.mk.ivents.business.dtos;

import com.mk.ivents.persistence.constants.EventCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AdvancedSearchRequest {
    private String title;
    private EventCategory eventCategory;
    private List<String> hashtags;
    private Instant takingPlaceStart;
    private Instant takingPlaceEnd;
}
