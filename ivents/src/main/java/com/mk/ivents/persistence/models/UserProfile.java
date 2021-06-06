package com.mk.ivents.persistence.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "email", nullable = false, length = 45, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 45, unique = true)
    private String phoneNumber;
}
