package com.mk.ivents.business.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
public class DateNumberStatPair {
    private Instant statDate;
    private int statNumber;
}
