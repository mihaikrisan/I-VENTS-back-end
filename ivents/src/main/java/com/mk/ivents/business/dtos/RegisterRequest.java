package com.mk.ivents.business.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @NotNull(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{5,19}$", message = "Invalid username")
    private String username;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]{8,15}$", message = "Invalid password")
    private String password;

    @NotBlank(message = "User role is required")
    @Pattern(regexp = "^CLIENT|ORGANIZER|ADMIN$", message = "Invalid user role")
    private String userRole;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "User role is required")
    private String lastName;

    @NotNull(message = "Age is required")
    @Min(value = 14, message = "Minimum age is 14")
    @Max(value = 90, message = "Maximum age is 90")
    private int age;

    @NotNull(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^(\\+|00)40[0-9]{9}$", message = "Invalid phone number")
    private String phoneNumber;
}
