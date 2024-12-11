package com.tokioschool.flightapp.flight.configuration.ut.mapper.ut;

import com.tokioschool.flightapp.flight.domain.Resource;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ResourceMapperConfigurationTest {
/*
    @Autowired
    private ModelMapper modelMapper;

    @Test
    void givenResource_whenMapperString_thenMapperSuccess() {
        final Resource resource = Resource.builder()
                .id(1L)
                .resourceId(UUID.randomUUID())
                .fileName("fileName")
                .size(20)
                .contentType("unknown")
                .build();

        final String strId = modelMapper.map(resource, String.class);

        assertThat(strId).isEqualTo(String.valueOf(resource.getId()));
    }

    @Test
    void givenString_whenMapperResource_thenMapperSuccess() {
        final String strId = "1";

        final Resource resource =modelMapper.map(strId, Resource.class);

        assertThat(resource).returns(Long.parseLong(strId),Resource::getId);
    }
 */
}
