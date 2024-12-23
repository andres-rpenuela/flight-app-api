package com.tokioschool.flightapp.store.scurity.configuration.userdetails.properties;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StoreUserConfigurationProperty.class)
public class StoreUserConfiguration {
}
