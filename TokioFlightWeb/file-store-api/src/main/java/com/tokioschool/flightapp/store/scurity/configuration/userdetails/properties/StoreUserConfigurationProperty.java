package com.tokioschool.flightapp.store.scurity.configuration.userdetails.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "application.store.login")
public record StoreUserConfigurationProperty(List<User> users){

    // clase interna de un usuario defindo en las properties
    public record User(String username,String password, List<String> authorities, List<String> roles ) {}
}
