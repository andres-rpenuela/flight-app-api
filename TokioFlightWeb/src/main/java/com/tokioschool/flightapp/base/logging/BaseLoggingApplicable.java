package com.tokioschool.flightapp.base.logging;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
//@Profile("dev") // componente solo disponible para el perfeil de dev
@Profile("test") // componente solo disponible para el perfeil de test
public class BaseLoggingApplicable {

    private final Environment environment;
    // se puede usar la antoación de Lombok
    //private static final Logger log = LoggerFactory.getLogger(BaseLoggingApplicable.class);

    @PostConstruct
    public void postConstruct() {

        log.info("BaseLoggingApplicable, {} profiles: [{}]",
                "info",
                String.join("",environment.getActiveProfiles()));
        log.trace("BaseLoggingApplicable, {}","trace");
        log.debug("BaseLoggingApplicable, {}","debug");
        log.info("BaseLoggingApplicable, {}","info");
        log.warn("BaseLoggingApplicable, {}","warn");
        log.error("BaseLoggingApplicable, {}","error");

        // se crea una excepción y se imprime
        //IllegalArgumentException exception = new IllegalArgumentException("This is my exception");
        // exception.printStackTrace(); // no es una traza de log por lo que se debe evitar
        //log.error("BaseLoggingApplicable, {}","error",exception);
        // throw exception;
    }
}
