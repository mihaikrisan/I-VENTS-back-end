package com.mk.ivents.business.services;

import com.mk.ivents.business.interfaces.SchedulerService;
import com.mk.ivents.persistence.models.Event;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {
    private static final long MUSIC_CATEGORY_EVENT_DURATION = 120;
    private static final long VISUAL_ARTS_CATEGORY_EVENT_DURATION = 60;
    private static final long PERFORMING_ARTS_CATEGORY_EVENT_DURATION = 90;
    private static final long FILM_CATEGORY_EVENT_DURATION = 150;
    private static final long LECTURES_AND_BOOKS_CATEGORY_EVENT_DURATION = 90;
    private static final long FASHION_CATEGORY_EVENT_DURATION = 180;
    private static final long FOOD_AND_DRINK_CATEGORY_EVENT_DURATION = 120;
    private static final long FESTIVALS_AND_FAIRS_CATEGORY_EVENT_DURATION = 1440;
    private static final long CHARITIES_CATEGORY_EVENT_DURATION = 120;
    private static final long SPORTS_AND_ACTIVE_LIFE_CATEGORY_EVENT_DURATION = 180;
    private static final long NIGHTLIFE_CATEGORY_EVENT_DURATION = 240;
    private static final long KIDS_AND_FAMILY_CATEGORY_EVENT_DURATION = 60;
    private static final long WORKSHOPS_CATEGORY_EVENT_DURATION = 120;
    private static final long GALAS_AND_CEREMONIES_CATEGORY_EVENT_DURATION = 180;
    private static final long OTHER_CATEGORY_EVENT_DURATION = 60;

    @Override
    public List<Event> generateSchedule(List<Event> eventList, Instant endTimeUpperBound) {
        return schedule(buildScheduleInput(eventList, endTimeUpperBound));
    }

    private List<Event> schedule(List<Object[]> scheduleInput) {
        List<Event> schedule = new ArrayList<>();

        if (scheduleInput.isEmpty()) {
            schedule = Collections.emptyList();

            return schedule;
        }

        scheduleInput.sort(Comparator.comparing(scheduleInputPart -> ((Instant) scheduleInputPart[1])));
        schedule.add((Event) scheduleInput.get(0)[0]);
        int previousSelectedEventIndex = 0;

        for (int i = 1; i < scheduleInput.size(); i++) {
            if (((Event) scheduleInput.get(i)[0]).getTakingPlaceTime().compareTo((Instant) scheduleInput
                    .get(previousSelectedEventIndex)[1]) >= 0) {
                previousSelectedEventIndex = i;
                schedule.add((Event) scheduleInput.get(i)[0]);
            }
        }

        return schedule;
    }

    private List<Object[]> buildScheduleInput(List<Event> eventList, Instant endTimeUpperBound) {
        List<Object[]> scheduleInput = new ArrayList<>();

        for (Event event : eventList) {
            long averageEventDuration = 0;
            switch (event.getEventCategory()) {
                case MUSIC: {
                    averageEventDuration = MUSIC_CATEGORY_EVENT_DURATION;

                    break;
                }

                case VISUAL_ARTS: {
                    averageEventDuration = VISUAL_ARTS_CATEGORY_EVENT_DURATION;

                    break;
                }

                case PERFORMING_ARTS: {
                    averageEventDuration = PERFORMING_ARTS_CATEGORY_EVENT_DURATION;

                    break;
                }

                case FILM: {
                    averageEventDuration = FILM_CATEGORY_EVENT_DURATION;

                    break;
                }

                case LECTURES_AND_BOOKS: {
                    averageEventDuration = LECTURES_AND_BOOKS_CATEGORY_EVENT_DURATION;

                    break;
                }

                case FASHION: {
                    averageEventDuration = FASHION_CATEGORY_EVENT_DURATION;

                    break;
                }

                case FOOD_AND_DRINK: {
                    averageEventDuration = FOOD_AND_DRINK_CATEGORY_EVENT_DURATION;

                    break;
                }

                case FESTIVALS_AND_FAIRS: {
                    averageEventDuration = FESTIVALS_AND_FAIRS_CATEGORY_EVENT_DURATION;

                    break;
                }

                case CHARITIES: {
                    averageEventDuration = CHARITIES_CATEGORY_EVENT_DURATION;

                    break;
                }

                case SPORTS_AND_ACTIVE_LIFE: {
                    averageEventDuration = SPORTS_AND_ACTIVE_LIFE_CATEGORY_EVENT_DURATION;

                    break;
                }

                case NIGHTLIFE: {
                    averageEventDuration = NIGHTLIFE_CATEGORY_EVENT_DURATION;

                    break;
                }

                case KIDS_AND_FAMILY: {
                    averageEventDuration = KIDS_AND_FAMILY_CATEGORY_EVENT_DURATION;

                    break;
                }

                case WORKSHOPS: {
                    averageEventDuration = WORKSHOPS_CATEGORY_EVENT_DURATION;

                    break;
                }

                case GALAS_AND_CEREMONIES: {
                    averageEventDuration = GALAS_AND_CEREMONIES_CATEGORY_EVENT_DURATION;

                    break;
                }

                case OTHER: {
                    averageEventDuration = OTHER_CATEGORY_EVENT_DURATION;

                    break;
                }
            }
            Instant endTime = event.getTakingPlaceTime().plus(Duration.ofMinutes(averageEventDuration));

            if (endTime.compareTo(endTimeUpperBound) <= 0) {
                Object[] scheduleInputPart = new Object[2];
                scheduleInputPart[0] = event;
                scheduleInputPart[1] = endTime;

                scheduleInput.add(scheduleInputPart);
            }
        }

        return scheduleInput;
    }
}
