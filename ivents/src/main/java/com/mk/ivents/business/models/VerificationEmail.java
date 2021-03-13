package com.mk.ivents.business.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class VerificationEmail {
    private String subject;
    private String recipient;
    private String tokenUuid;
}
