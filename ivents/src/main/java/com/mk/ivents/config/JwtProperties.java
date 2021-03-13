package com.mk.ivents.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Setter
@Getter
public class JwtProperties {

    /**
     * Time in milliseconds after which a jwt should expire.
     */
    private long expirationtime = 900000;
}
