package com.mk.ivents.business.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
@Setter
public class Password {

    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]{8,15}$", message = "Invalid password")
    private String password;
}
