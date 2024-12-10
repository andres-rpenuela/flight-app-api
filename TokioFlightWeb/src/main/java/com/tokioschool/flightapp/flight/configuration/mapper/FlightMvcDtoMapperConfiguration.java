package com.tokioschool.flightapp.flight.configuration.mapper;

import com.tokioschool.flightapp.flight.configuration.mapper.converter.FligthDTOToFlightMvcDTOConverter;
import com.tokioschool.flightapp.flight.dto.FlightDTO;
import com.tokioschool.flightapp.flight.dto.FlightMvcDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightMvcDtoMapperConfiguration {

    private final ModelMapper modelMapper;

    @Autowired
    public FlightMvcDtoMapperConfiguration(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configure();
    }

    private void configure(){
        modelMapper.typeMap(FlightDTO.class, FlightMvcDTO.class)
                        .setConverter(new FligthDTOToFlightMvcDTOConverter());
    }
}
