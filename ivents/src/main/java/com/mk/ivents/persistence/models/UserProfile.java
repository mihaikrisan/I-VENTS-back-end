package com.mk.ivents.persistence.models;

import com.mk.ivents.persistence.constants.EventCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_id")
    private int id;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 45)
    private String phoneNumber;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "preferred_event_category", joinColumns = @JoinColumn(name = "user_profile_id"))
    @Column(name = "event_category", nullable = false)
    private List<EventCategory> preferredEventCategories;
}
