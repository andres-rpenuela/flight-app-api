package com.tokioschool.flightapp.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@ConfigurationProperties(prefix = "application.batch")
public record AirportBatchConfigurationProperties(Path absolutePath,String relativePath) {


    public Path getAirportsCsvPath(){
        return Path.of(absolutePath.toString(),"airports.csv");
    }

    public Path getAirportBathPath(){
        return loadFileAirportBathPath(StringUtils.EMPTY);
    }

    public Path loadFileAirportBathPath(String fileName){
        return Optional.ofNullable(relativePath)
                .map(FileSystems.getDefault()::getPath)
                .map(path -> path.resolve(fileName))
                .orElse(FileSystems.getDefault().getPath(StringUtils.EMPTY));
    }

    public Path getExportPath(){
        return getExportPath(null);
    }

    public Path getExportPath(String fileName){
        return Optional.ofNullable(relativePath)
                .filter(s -> s.contains(".json"))
                .map(s -> Paths.get(s,"exports"))
                .map(path -> path.resolve(fileName))
                .orElse(FileSystems.getDefault().getPath("..\\batch-working-files\\exports", UUID.randomUUID() + ".json"));
    }
}
