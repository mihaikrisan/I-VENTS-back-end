package com.mk.ivents.business.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class Email {

    @NotNull(message = "Email is required")
    @javax.validation.constraints.Email(message = "Email is not valid")
    private String emailAddress;
}
