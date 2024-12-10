package com.tokioschool.flightapp.flight.configuration.ut.mapper.ut;

import com.tokioschool.flightapp.flight.configuration.mapper.converter.ResourceToStringConvert;
import com.tokioschool.flightapp.flight.configuration.mapper.converter.StringToResourceConvert;
import com.tokioschool.flightapp.flight.domain.Airport;
import com.tokioschool.flightapp.flight.domain.Flight;
import com.tokioschool.flightapp.flight.domain.Resource;
import com.tokioschool.flightapp.flight.dto.FlightDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FlightMapperTest {
    private ModelMapper modelMapper;

    @BeforeEach
    public void beforeEach(){
        this.modelMapper = new ModelMapper();
        this.modelMapper.addConverter(new ResourceToStringConvert());
        this.modelMapper.addConverter(new StringToResourceConvert());
    }

    @Test
    void givenFlight_whenMapperFlightDto_thenMapperSuccess() {
        final Resource resource = Resource.builder()
                .id(1L)
                .resourceId(UUID.randomUUID())
                .fileName("fileName")
                .size(20)
                .contentType("unknown")
                .build();
        final Airport airportArrival = Airport.builder()
                .acronym("arrival")
                .longitude(BigDecimal.ONE)
                .latitude(BigDecimal.ONE)
                .country("SPAIN")
                .build();
        final Airport airportDeparture= Airport.builder()
                .acronym("arrival")
                .longitude(BigDecimal.ONE)
                .latitude(BigDecimal.ONE)
                .country("SPAIN")
                .build();

        final Flight flight = Flight.builder()
                .id(1L)
                .number("number")
                .airportArrival(airportArrival)
                .airportDeparture(airportDeparture)
                .flightImg(resource)
                .build();

        final FlightDTO flightDTO = modelMapper.map(flight, FlightDTO.class);

        assertThat(flightDTO)
                .isNotNull()
                .returns("1" ,FlightDTO::getFlightImg)
                .returns("number",FlightDTO::getNumber)
                .returns(airportArrival.getAcronym(),FlightDTO::getAirportArrivalAcronym)
                .returns(airportDeparture.getAcronym(),FlightDTO::getAirportDepartureAcronym);
    }
    @Test
    void givenFlightDto_whenMapperFlight_thenMapperSuccess() {
        final FlightDTO flightDTO = FlightDTO.builder()
                .id(1L)
                .number("number")
                .airportArrivalAcronym("arrival")
                .airportDepartureAcronym("departure")
                .capacity(1)
                .occupancy(1)
                .flightImg("1").build();

        final Flight flight = modelMapper.map(flightDTO,Flight.class);
        assertThat(flight)
                .isNotNull()
                .returns(Long.parseLong(flightDTO.getFlightImg()) ,item -> item.getFlightImg().getId())
                .returns(flightDTO.getNumber(),Flight::getNumber)
                .returns(flightDTO.getAirportArrivalAcronym(),item->item.getAirportArrival().getAcronym())
                .returns(flightDTO.getAirportDepartureAcronym(),item->item.getAirportDeparture().getAcronym());
    }


}
