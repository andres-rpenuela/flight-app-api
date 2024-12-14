package com.tokioschool.flightapp.flight.store.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "application.store")
public record StoreConfigurationProperties(String baseUrl, User user) {

    public record User(String username,String password){}
}
