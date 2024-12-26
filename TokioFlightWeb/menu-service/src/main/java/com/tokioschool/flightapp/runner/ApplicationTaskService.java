package com.tokioschool.flightapp.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Tarea que se ejecuta cuando inicia la aplicaiocn de menu service, y
 * cuando finaliza, se cierra la aplicación, es la aplicaicon
 * mas basica de spring boot
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationTaskService implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
            log.info("ApplicationTaskService started");
    }
}
