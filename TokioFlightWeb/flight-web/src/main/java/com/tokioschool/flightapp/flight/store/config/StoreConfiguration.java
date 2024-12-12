package com.tokioschool.flightapp.flight.store.config;

import com.tokioschool.flightapp.flight.store.config.properties.StoreConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StoreConfigurationProperties.class)
public class StoreConfiguration {
}
