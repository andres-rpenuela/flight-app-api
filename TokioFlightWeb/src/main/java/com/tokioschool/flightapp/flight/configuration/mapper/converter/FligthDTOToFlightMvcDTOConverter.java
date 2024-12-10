package com.tokioschool.flightapp.flight.configuration.mapper.converter;

import com.tokioschool.flightapp.flight.domain.Resource;
import com.tokioschool.flightapp.flight.dto.FlightDTO;
import com.tokioschool.flightapp.flight.dto.FlightMvcDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class FligthDTOToFlightMvcDTOConverter implements Converter<FlightDTO, FlightMvcDTO> {


    @Override
    public FlightMvcDTO convert(MappingContext<FlightDTO,FlightMvcDTO> mappingContext) {
        return Optional.ofNullable(mappingContext)
                .map(MappingContext::getSource)
                .map(FligthDTOToFlightMvcDTOConverter::buildFlightDto)
                .orElse(null);
    }

    private static FlightMvcDTO buildFlightDto(FlightDTO flightDto) {
        return FlightMvcDTO.builder()
                .number(flightDto.getNumber())
                .capacity(flightDto.getCapacity())
                .status(flightDto.getStatus().name())
                //.dayTime(flightDto.getDepartureTime().format(DateTimeFormatter.ISO_DATE_TIME)) // Temp., a String
                .dayTime(flightDto.getDepartureTime())
                .airportArrivalAcronym(flightDto.getAirportArrivalAcronym())
                .airportDepartureAcronym(flightDto.getAirportDepartureAcronym())
                .id(flightDto.getId())
                .build();
    }

}
