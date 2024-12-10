package com.tokioschool.flightapp.flight.configuration.mapper;

import com.tokioschool.flightapp.flight.domain.Resource;
import com.tokioschool.flightapp.flight.configuration.mapper.converter.ResourceToStringConvert;
import com.tokioschool.flightapp.flight.configuration.mapper.converter.StringToResourceConvert;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceMapperConfiguration {

    private final ModelMapper modelMapper;

    @Autowired
    public ResourceMapperConfiguration(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configure();
    }

    private void configure(){
        modelMapper.typeMap(Resource.class, String.class)
                .setConverter(mappingContext -> new ResourceToStringConvert().convert(mappingContext));
        modelMapper.typeMap(String.class,Resource.class)
                .setConverter(mappingContext -> new StringToResourceConvert().convert(mappingContext));
    }
}
