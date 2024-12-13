package com.tokioschool.flightapp.store.scurity.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "application.jwt")
public record JwtConfigurationProperty(String secret, Duration expiration) {
}
