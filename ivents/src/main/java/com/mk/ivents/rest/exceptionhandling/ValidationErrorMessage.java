package com.mk.ivents.rest.exceptionhandling;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ValidationErrorMessage {
    private List<ValidationError> validationErrors;
}
