package com.tokioschool.flightapp.batch.importer;

import com.tokioschool.flightapp.config.AirportBatchConfigurationProperties;
import com.tokioschool.flightapp.csv.AirportCsv;
import com.tokioschool.flightapp.csv.AirportCsvMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * Class that defined all bath of Airport Csv
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AirportCsvImporterBatchConfiguration {

    private final AirportBatchConfigurationProperties airportBatchConfigurationProperties;

    /**
     * Proceso de lectura de los datos
     * @return
     */
    @Bean
    public FlatFileItemReader<AirportCsv> airportCsvFlatFileItemReader(){
        Path aiportCsvPath = airportBatchConfigurationProperties.loadFileAirportBathPath("airport.csv");

        FlatFileItemReader<AirportCsv> flatFileItemReader =  new FlatFileItemReader<AirportCsv>();

        // saltar la primera linea
        flatFileItemReader.setLinesToSkip(1);
        // codificacion
        flatFileItemReader.setEncoding(StandardCharsets.UTF_8.name());
        // fichero a leer
        flatFileItemReader.setResource(new PathResource(aiportCsvPath));
        // lineas a leer y como se van a leer
        DefaultLineMapper<AirportCsv> lineMapper = new DefaultLineMapper<>();

        // a. como estan separadas los datos de una linea y nombres de cada uno
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        // a.1. separacion de cada token
        lineTokenizer.setDelimiter(",");
        // a.2. nombres de cada uno de los token que se reciben, requiere un mapper
        lineTokenizer.setNames(AirportCsvMapper.AIRPORT_CSV_FIELDS);

        // b. establecer la lecutra de cada linea y como mappear
        lineMapper.setFieldSetMapper(new AirportCsvMapper());
        lineMapper.setLineTokenizer(lineTokenizer);

        // c. establecer el mapper a usar por la implementacion
        flatFileItemReader.setLineMapper(lineMapper);

        return flatFileItemReader;
    }
}
