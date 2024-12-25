package com.tokioschool.flightapp.batch.exporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokioschool.flightapp.config.AirportBatchConfigurationProperties;
import com.tokioschool.flightapp.domain.AirportRaw;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Class that defined all bath of Airport Export Json
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AirportRawExporterBatchConfiguration {

    private final AirportBatchConfigurationProperties airportBatchConfigurationProperties;
    private final EntityManagerFactory  entityManagerFactory;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    /**
     * process to reader
     * obtiene los elementos leidos en la última ejecuion del import
     * @return
     */
    @Bean
    public JpaPagingItemReader<AirportRaw> airportRawJpaPagingItemReader(){
        JpaPagingItemReader<AirportRaw> itemReader= new JpaPagingItemReader<AirportRaw>();
        itemReader.setEntityManagerFactory(entityManagerFactory);
        // tamaño de elementos de la pagina
        itemReader.setPageSize(10);
        // query to reference entity
        itemReader.setQueryString(
        """
        SELECT a FROM AirportRaw a \
        WHERE a.airportRawId.jobId = (SELECT MAX(b.airportRawId.jobId) FROM AirportRaw b) \
        ORDER BY a.airportRawId.acronym ASC
        """);

        return itemReader;
    }

    // El ItemProcess, no es necesario, porque no se queire manipular los datos

    /**
     * Proceso de lecutra
     * @return
     */
    @Bean
    public JsonFileItemWriter<AirportRaw> airportRawJsonFileItemWriter(){

        return new JsonFileItemWriterBuilder<AirportRaw>()
                // Se indica el mecanismo de serialización del objeto
                .jsonObjectMarshaller(
                        // se añade a un objeto de tipo Jackson
                        new JacksonJsonObjectMarshaller<>(
                                // se añada la dependencia "jackson-databind"
                                // al no tener spring boot en el modulo
                                new ObjectMapper()
                        ))
                .resource(
                        new FileSystemResource(
                                airportBatchConfigurationProperties.getExportPath()
                        ))
                // nombre del proceso
                .name("airportRawJsonFileItemWriter")
                .build();
    }

    @Bean
    public Step exportAirportsStep(
            ItemReader<AirportRaw> itemReader,
            JobRepository jobRepository){

        return new StepBuilder("export-airport-raw-step",jobRepository)
                .<AirportRaw,AirportRaw>chunk(10,platformTransactionManager)
                .reader(itemReader)
                .writer(airportRawJsonFileItemWriter())
                .build();
    }

    @Bean
    public Job exportAirportJob(Step exportAirportsStep){
        return new JobBuilder("export-airports-raw-job",jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(exportAirportsStep)
                .build();
    }
}
