package com.mk.ivents.rest.exceptionhandling;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ValidationError {
    private String fieldName;
    private Object rejectedValue;
    private String errorMessage;
}
