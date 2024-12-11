package com.tokioschool.flightapp.store.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokioschool.flightapp.store.configuration.StoreConfigurationProperties;
import com.tokioschool.flightapp.store.domain.ResourceDescription;
import com.tokioschool.flightapp.store.dto.ResourceContentDto;
import com.tokioschool.flightapp.store.dto.ResourceIdDto;
import com.tokioschool.flightapp.store.service.StoreService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class StoreServiceImp implements StoreService {

    private final ObjectMapper objectMapper;
    private final StoreConfigurationProperties storeConfigurationProperties;

    @Override
    public Optional<ResourceIdDto> saveResource(MultipartFile multipartFile, @Nullable String description) {
        if(multipartFile.isEmpty()){
            log.debug("multipartFile is empty");
            return Optional.empty();
        }
        final ResourceDescription resourceDescription = ResourceDescription.builder()
                .resourceName(multipartFile.getOriginalFilename())
                .description(description)
                .size((int) multipartFile.getSize())
                .contentType(multipartFile.getContentType()).build();

        final ResourceIdDto resourceIdDto = ResourceIdDto.builder().resourceId(UUID.randomUUID()).build();
        final String resourceName = "%s".formatted(resourceIdDto.resourceId());
        final String resourceDescriptionName = "%s.json".formatted(resourceIdDto.resourceId());

        final Path pathResourceToContent  = storeConfigurationProperties.getdResourcePathFromRelativePathGivenNameResource(resourceName);
        final Path pathResourceToDescription = storeConfigurationProperties.getdResourcePathFromRelativePathGivenNameResource(resourceDescriptionName);

        try {
            Files.write(pathResourceToContent,multipartFile.getBytes());

        } catch (IOException e) {
            log.error("Don't save content resource, cause: %s".formatted(e));
            return Optional.empty();
        }

        try {
            // dependia de ObjectMapper de la libería jackson
            // guarda el binding "resourceDescription" con como un fichero json "pathToDescription.toFile()"
            objectMapper.writeValue(pathResourceToDescription.toFile(),resourceDescription);
        } catch (IOException e) {
            log.error("Don't save meta data of resource, cause: %s".formatted(e));
            try {
                Files.deleteIfExists(pathResourceToContent);
            } catch (IOException ex) {
                log.error("Error to deleted the resource, cause: %s".formatted(e));
            }
            return Optional.empty();
        }
        return Optional.of(resourceIdDto);
    }

    @Override
    public Optional<ResourceContentDto> findResource(UUID resourceId) {
        final FilenameFilter filenameFilter = (dir, name) -> name.contains("%s".formatted(resourceId));
        final Optional<File> fileResourceOpt;
        final Optional<File> fileDescriptionResourceOpt;

        final File[] filesByName = storeConfigurationProperties.buildResourcePathFromRelativePathGivenNameResource()
                .toFile().listFiles(filenameFilter);

        if(Objects.isNull(filesByName)){
            log.debug("No files found");
            return Optional.empty();
        }

        fileResourceOpt = Arrays
                .stream(filesByName)
                .filter(file -> file.getName().equals(resourceId.toString())).findFirst();

        fileDescriptionResourceOpt = Arrays
                .stream(filesByName)
                .filter(file -> file.getName().contains(".json")).findFirst();

        if(fileResourceOpt.isEmpty() || fileDescriptionResourceOpt.isEmpty()){
            log.debug("No files found");
            return Optional.empty();
        }

        try {
            final byte[] content = Files.readAllBytes(fileResourceOpt.get().toPath());

            final ResourceDescription resourceDescription = this.objectMapper
                    .readValue(fileDescriptionResourceOpt.get(), ResourceDescription.class);

            // build the result end, after read resources
            final ResourceContentDto resourceContentDto = ResourceContentDto.builder()
                    .contentType(resourceDescription.getContentType())
                    .description(resourceDescription.getDescription())
                    .resourceName(resourceDescription.getResourceName())
                    .size(resourceDescription.getSize())
                    .content(content)
                    .resourceId(resourceId)
                    .build();

            return Optional.of(resourceContentDto);

        } catch (IOException e) {
            log.debug("Error to read resource or description resource, cause %s".formatted(e));
            return Optional.empty();
        }

    }

    @Override
    public void deleteResource(UUID resourceId) {
        final FilenameFilter filenameFilter = (dir, name) -> name.contains("%s".formatted(resourceId));
        final File[] filesByName = storeConfigurationProperties
                .buildResourcePathFromRelativePathGivenNameResource()
                .toFile()
                .listFiles(filenameFilter::accept);

        Arrays.stream(filesByName).forEach(file->{
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException e) {
                log.error("Error in deleteResource, cause: ".formatted(e));
            }
        });
    }
}
