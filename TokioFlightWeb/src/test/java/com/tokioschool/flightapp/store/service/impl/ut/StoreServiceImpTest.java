package com.tokioschool.flightapp.store.service.impl.ut;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokioschool.flightapp.store.configuration.StoreConfigurationProperties;
import com.tokioschool.flightapp.store.dto.ResourceContentDto;
import com.tokioschool.flightapp.store.dto.ResourceIdDto;
import com.tokioschool.flightapp.store.service.impl.StoreServiceImp;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

class StoreServiceImpTest {

    private StoreServiceImp storeService;

    @TempDir public Path temporalPath;

    // CONSTANTS
    public static final String EXT_TXT = ".txt";
    private static final String FILE_NAME = "file%s".formatted(EXT_TXT);
    private static final String CONTENT = "HOLA";
    private static final String CONTENT_TYPE = MediaType.TEXT_PLAIN_VALUE;

    @BeforeEach
    void beforeEach() throws Exception {
        // add dependencies to service
        final StoreConfigurationProperties resourceConfigProperty =
                new StoreConfigurationProperties(temporalPath, temporalPath.toAbsolutePath().toString());

        final ObjectMapper objectMapper = new ObjectMapper();
        storeService = new StoreServiceImp(objectMapper,resourceConfigProperty);
    }

    @Test
    void givenResource_whenSaveResource_thenReturnOk() {
        final MockMultipartFile mockMultipartFile = getMockMultipartFile();

        Optional<ResourceIdDto> optionalResourceIdDto = storeService.saveResource(mockMultipartFile,"description");

        final StoreConfigurationProperties resourceConfigProperty =
                new StoreConfigurationProperties(temporalPath, temporalPath.toAbsolutePath().toString());

        Assertions.assertThat(optionalResourceIdDto)
                .isNotNull()
                .isNotEmpty()
                .matches(resourceIdDto -> Objects.nonNull(resourceIdDto.get().resourceId()))
                .matches(resourceIdDto ->
                        Files.exists(resourceConfigProperty
                                .getdResourcePathFromRelativePathGivenNameResource(
                                        resourceIdDto.get().resourceId().toString())
                        )
                ).matches(resourceIdDto ->
                        Files.exists(resourceConfigProperty
                                .getdResourcePathFromRelativePathGivenNameResource("%s.json"
                                        .formatted(resourceIdDto.get().resourceId().toString())
                                )
                        )
                );

    }

    @Test
    void givenResource_whenFindResource_thenReturnOk() {
        final ResourceIdDto resourceIdDto = storeService
                .saveResource(
                        getMockMultipartFile(),
                        "description").get();

        final Optional<ResourceContentDto> optionalResourceContentDto = storeService
                .findResource(resourceIdDto.resourceId());

        Assertions.assertThat(optionalResourceContentDto)
                .isPresent()
                .isNotEmpty()
                .get()
                .returns(resourceIdDto.resourceId(),ResourceContentDto::resourceId)
                .returns(CONTENT.getBytes(),ResourceContentDto::content)
                .returns(FILE_NAME,ResourceContentDto::resourceName)
                .returns(CONTENT_TYPE,ResourceContentDto::contentType);
    }

    @Test
    void givenResource_whenDeleteResource_thenReturnOk() {
        final ResourceIdDto resourceIdDto = storeService
                .saveResource(getMockMultipartFile(), "description").get();

        storeService.deleteResource(resourceIdDto.resourceId());

        // verify
        final Optional<ResourceContentDto> optionalResourceContentDto = storeService
                .findResource(resourceIdDto.resourceId());

        Assertions.assertThat(optionalResourceContentDto)
                .isEmpty();
    }

    private static MockMultipartFile getMockMultipartFile() {

        return new MockMultipartFile(
                FILE_NAME.replace(EXT_TXT, StringUtils.EMPTY),
                FILE_NAME,
                CONTENT_TYPE,
                CONTENT.getBytes());

    }
}