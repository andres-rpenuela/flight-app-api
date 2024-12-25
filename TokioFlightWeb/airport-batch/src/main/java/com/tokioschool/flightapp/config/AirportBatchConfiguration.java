package com.tokioschool.flightapp.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {AirportBatchConfigurationProperties.class})
public class AirportBatchConfiguration {
}
