package com.mk.ivents.rest.exceptionhandling;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
