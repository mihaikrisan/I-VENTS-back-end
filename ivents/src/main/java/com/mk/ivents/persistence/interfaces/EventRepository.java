package com.mk.ivents.persistence.interfaces;

import com.mk.ivents.persistence.constants.EventCategory;
import com.mk.ivents.persistence.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    static Specification<Event> titleContains(String title) {
        return (event, cq, cb) -> {
            if (title == null) {
                return null;
            }

            return cb.like(event.get("title"), "%" + title + "%");
        };
    }

    static Specification<Event> hasCategory(EventCategory eventCategory) {
        return (event, cq, cb) -> {
            if (eventCategory == null) {
                return null;
            }

            return cb.equal(event.get("eventCategory"), eventCategory);
        };
    }

    static Specification<Event> isTakingPlaceBetween(Instant intervalStart, Instant intervalEnd) {
        return (event, cq, cb) -> {
            if (intervalStart == null && intervalEnd == null) {
                return null;
            }

            if (intervalEnd == null) {
                return cb.greaterThanOrEqualTo(event.get("takingPlaceTime"), intervalStart);
            }

            return cb.between(event.get("takingPlaceTime"), intervalStart != null ? intervalStart : Instant.now(),
                    intervalEnd);
        };
    }

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

    List<Event> findByTakingPlaceTimeBetween(Instant startTime, Instant endTime);

    default List<Event> advancedSearch(String title, EventCategory eventCategory, Instant takingPlaceStart,
                                       Instant takingPlaceEnd) {
        return findAll(Specification
                .where(titleContains(title))
                .and(hasCategory(eventCategory))
                .and(isTakingPlaceBetween(takingPlaceStart, takingPlaceEnd))
        );
    }
}
