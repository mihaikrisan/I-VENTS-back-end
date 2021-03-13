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
}
