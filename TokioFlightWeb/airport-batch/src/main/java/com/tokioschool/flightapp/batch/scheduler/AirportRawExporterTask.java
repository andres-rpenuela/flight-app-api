package com.tokioschool.flightapp.batch.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Componente para lanzar manualmente los jobs
 */
@Component
@RequiredArgsConstructor
public class AirportRawExporterTask {

    // implementacion de sping para lanzar un job
    private final JobLauncher jobLauncher;
    // nombre del job a lanzar, por defecto, el nombre del metodo
    private final Job exportAirportJob;

    @Scheduled(
            cron = "0 */1 * * * *"
    )
    public void launchAirportRawExport(){

        JobParameters jobParameter = new JobParametersBuilder()
                .addString("jobExecutionId", UUID.randomUUID().toString())
                .toJobParameters();
        try {
            jobLauncher.run(exportAirportJob,jobParameter);
        } catch (JobExecutionAlreadyRunningException
                 | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException
                 | JobRestartException e) {
            throw new RuntimeException(e);
        }
    }
}
