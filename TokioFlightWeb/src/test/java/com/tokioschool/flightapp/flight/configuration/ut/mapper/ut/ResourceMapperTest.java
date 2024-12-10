package com.tokioschool.flightapp.flight.configuration.ut.mapper.ut;

import com.tokioschool.flightapp.flight.configuration.ModelMapperBeanConfiguration;
import com.tokioschool.flightapp.flight.configuration.mapper.ResourceMapperConfiguration;
import com.tokioschool.flightapp.flight.configuration.mapper.converter.ResourceToStringConvert;
import com.tokioschool.flightapp.flight.configuration.mapper.converter.StringToResourceConvert;
import com.tokioschool.flightapp.flight.domain.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceMapperTest {


    private ModelMapper modelMapper;

    @BeforeEach
    public void beforeEach(){
        // Option A: Confing manually
        //this.modelMapper = new ModelMapper();
        //this.modelMapper.addConverter(new ResourceToStringConvert());
        //this.modelMapper.addConverter(new StringToResourceConvert());

        // Option B: Using configuration in class
        this.modelMapper = new ModelMapperBeanConfiguration().modelMapper();
        new ResourceMapperConfiguration(modelMapper);
    }

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
}
