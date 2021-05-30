package com.mk.ivents.persistence.models;

import com.mk.ivents.persistence.constants.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name", nullable = false, length = 20, unique = true)
    private String username;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false, length = 20)
    private UserRole userRole;

    @Column(name = "is_enabled", nullable = false)
    private boolean isEnabled;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    @JoinColumn(name = "user_profile_id", nullable = false)
    private UserProfile userProfile;

    @ManyToMany(mappedBy = "usersInterested")
    private Set<Event> interestedInEvents;

    @ManyToMany(mappedBy = "usersGoing")
    private Set<Event> goingToEvents;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "favorite_event", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")})
    private Set<Event> favoriteEvents;

    public void addInterestedInEvent(Event event) {
        interestedInEvents.add(event);
        event.getUsersInterested().add(this);
    }

    public void removeInterestedInEvent(Event event) {
        interestedInEvents.remove(event);
        event.getUsersInterested().remove(this);
    }

    public void addGoingToEvent(Event event) {
        goingToEvents.add(event);
        event.getUsersGoing().add(this);
    }

    public void removeGoingToEvent(Event event) {
        goingToEvents.remove(event);
        event.getUsersGoing().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
