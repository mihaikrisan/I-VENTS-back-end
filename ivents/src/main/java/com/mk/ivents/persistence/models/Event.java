package com.mk.ivents.persistence.models;

import com.mk.ivents.persistence.constants.EventCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
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

    @Column(name = "event_title", nullable = false, length = 45)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_category", nullable = false, length = 45)
    private EventCategory eventCategory;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hashtag", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "hashtag", nullable = false)
    private List<String> hashtags;

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

    @Column(name = "description", length = 2048, nullable = false)
    private String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "interested_in_event", joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> usersInterested;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "going_to_event", joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> usersGoing;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizer_id", nullable = false)
    private User organizer;

    public void addInterestedUser(User user) {
        usersInterested.add(user);
        user.getInterestedInEvents().add(this);
    }

    public void removeInterestedUser(User user) {
        usersInterested.remove(user);
        user.getInterestedInEvents().remove(this);
    }

    public void addGoingUser(User user) {
        usersGoing.add(user);
        user.getGoingToEvents().add(this);
    }

    public void removeGoingUser(User user) {
        usersGoing.remove(user);
        user.getGoingToEvents().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return getId() == event.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
