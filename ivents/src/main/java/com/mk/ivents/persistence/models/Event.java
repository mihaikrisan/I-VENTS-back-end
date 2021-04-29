package com.mk.ivents.persistence.models;

import com.mk.ivents.persistence.constants.EventCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int id;

    @Column(name = "event_name", nullable = false, length = 45)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_category", nullable = false, length = 45)
    private EventCategory eventCategory;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hashtag", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "hashtag", nullable = false)
    private List<String> hashtags;

    @Column(name = "minimum_age")
    private Integer minimumAge;

    @Column(name = "max_number_of_persons")
    private Integer maxNumberOfPersons;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @Column(name = "takes_place_on_utc", nullable = false)
    private Instant takingPlaceTime;

    @Column(name = "added_on_utc", nullable = false)
    private Instant addedTime;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "interestedInEvents")
    private Set<User> usersInterested;

    @ManyToMany(mappedBy = "goingToEvents")
    private Set<User> usersGoing;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Event event = (Event) o;
//        return getId() == event.getId();
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId());
//    }
}
