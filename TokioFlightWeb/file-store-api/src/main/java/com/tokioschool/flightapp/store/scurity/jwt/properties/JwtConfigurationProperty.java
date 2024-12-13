package com.tokioschool.flightapp.store.scurity.jwt.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "application.jwt")
public record JwtConfigurationProperty(String secret, Duration expiration) {
}
