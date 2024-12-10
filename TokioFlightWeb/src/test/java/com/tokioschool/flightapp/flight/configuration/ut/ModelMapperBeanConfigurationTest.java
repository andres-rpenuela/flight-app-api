package com.tokioschool.flightapp.flight.configuration.ut;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ModelMapperBeanConfigurationTest {

    @Autowired
    ModelMapper modelMapper;

    @Test
    void givenModelMapper_whenStartApplication_thenModelMapperCreateBeanModelMapper() {

        assertThat(modelMapper).isNotNull();
    }
}
