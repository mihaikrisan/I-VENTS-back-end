package com.mk.ivents.business.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class MonthlyStats {
    private List<DateNumberStatPair> usersGoingStats;
    private List<DateNumberStatPair> usersInterestedStats;
}
