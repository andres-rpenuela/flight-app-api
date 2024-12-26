package com.tokioschool.flightapp;

import com.tokioschool.flightapp.batch.importer.AirportCsvImporterBatchConfiguration;
import com.tokioschool.flightapp.batch.listener.AirportCsvImporterBatchListener;
import com.tokioschool.flightapp.config.AirportBatchConfigurationProperties;
import com.tokioschool.flightapp.domain.AirportRaw;
import com.tokioschool.flightapp.domain.AirportRawId;
import com.tokioschool.flightapp.reposiroty.AirportRawRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;

/**
 * Test de integrecacion, al comprobar el funcionamiento del import del batch completo
 */
@SpringBatchTest // Contexto de batch
// elementos que carga al levnatar el text
// clase a testear + dependencias
@ContextConfiguration(classes = {
        // clase donde se define el batch
        AirportCsvImporterBatchConfiguration.class,
        // dependencias
        AirportCsvImporterBatchListener.class,
        AirportCsvImporterBatchITest.Init.class
})
// comportamiento de la base de datos
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb2;MODE=MYSQL;DATABASE_TO_LOWER=TRUE;",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
// configuracion de tablas de batch, esta anotacion esta por defecto en @SpringBootApplication
@EnableAutoConfiguration
// Gestion de entidades y repositorios
// se ignora por el ContextConfiguration, por lo que se debe habilitar
@EntityScan(basePackages = {"com.tokioschool.flightapp.domain"})
@EnableJpaRepositories(basePackages = {"com.tokioschool.flightapp.reposiroty"})
// perfil
//@ActiveProfiles("test")
public class AirportCsvImporterBatchITest {

    @Autowired private Job  importAirportCsvJob;
    // lanzar el job
    @Autowired private JobLauncherTestUtils jobLauncherTestUtils;
    // ejecuciones del job
    @Autowired private JobRepositoryTestUtils jobRepositoryTestUtils;
    // reppositorio
    @Autowired private AirportRawRepository airportRawRepository;

    @BeforeEach
    void beforeEach(){
        // limpiar el contexto
        jobLauncherTestUtils.setJob(importAirportCsvJob);
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    void givenNewJob_whenExecuted_thenOk() throws Exception {
        // ejecucion
        JobParameters parameters = new JobParametersBuilder().toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        // validacion
        Assertions.assertThat(jobExecution.getExitStatus())
                .isEqualTo(ExitStatus.COMPLETED);

        long count = airportRawRepository.count();
        Assertions.assertThat(count).isGreaterThanOrEqualTo(36L);

        // que se ha mapeado bien
        List<AirportRaw> bcnList = airportRawRepository.findAll(
                // busqueda por objecto especifico
                // por acronimo por ejemplo
                Example.of(AirportRaw.builder().country("CN").build())
        );

        Assertions.assertThat(bcnList).hasSizeGreaterThan(1)
                .first()
                .returns(jobExecution.getJobId(), o -> o.getAirportRawId().getJobId())
                .returns("BPL",o -> o.getAirportRawId().getAcronym() )
                .returns("Bole Alashankou Airport", AirportRaw::getName)
                .returns("CN", AirportRaw::getCountry)
                .satisfies(
                        o ->
                                Assertions.assertThat(o.getLat())
                                        .usingComparator(BigDecimal::compareTo)
                                        .isEqualTo(BigDecimal.valueOf(44.90))
                )
                .satisfies(
                        o ->
                                Assertions.assertThat(o.getLon())
                                        .usingComparator(BigDecimal::compareTo)
                                        .isEqualTo(BigDecimal.valueOf(82.30))
                );
    }

    @TestConfiguration
    public static class Init {
        /**
         * Dependencias
         *     private final AirportBatchConfigurationProperties airportBatchConfigurationProperties;
         *     private final AirportCsvImporterBatchListener airportCsvImporterBatchListener;
         *
         * Proprocionas por spring batch:
         *     private final EntityManagerFactory entityManagerFactory;
         *     private final JobRepository jobRepository;
         *     private final PlatformTransactionManager platformTransactionManager;
         */
        // bien que utiliza el filtro de la cena
        @Bean
        public AirportBatchConfigurationProperties airportBatchConfigurationProperties() throws URISyntaxException {
            URI uri = this.getClass().getResource("/data").toURI();
            Path path = Path.of(uri);
            return new AirportBatchConfigurationProperties(path,path.toString());
        }
    }
}
