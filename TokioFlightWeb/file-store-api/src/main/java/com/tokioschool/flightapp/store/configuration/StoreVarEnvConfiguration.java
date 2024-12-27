package com.tokioschool.flightapp.store.configuration;

import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class StoreVarEnvConfiguration {

    @Value("${APPLICATION_CUSTOM}")
    private String applicationCustom;

    @PostConstruct
    public void init(){
        log.info("application-custom: {} ", applicationCustom);
    }
}
