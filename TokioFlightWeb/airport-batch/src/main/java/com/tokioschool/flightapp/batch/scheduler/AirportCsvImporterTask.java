package com.tokioschool.flightapp.batch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AirportCsvImporterTask {

    // implementacion de sping para lanzar un job
    private final JobLauncher jobLauncher;
    // job a lanzar en la tarea
    private final Job importAirportCsvJob;

    @Scheduled(initialDelay = 15, timeUnit = TimeUnit.SECONDS)
    public void launchImporterAirportCsvJob(){
        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();

        try {
            jobLauncher.run(importAirportCsvJob,jobParameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
