package com.tokioschool.flightapp.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FlightDTO {

    private Long id;
    private int version;
    private int capacity;
    private String number;
    private int occupancy;
    private String flightImg;
    private STATUS_FLIGHT_DTO status;
    private LocalDateTime departureTime;
    private String airportDepartureAcronym;
    private String airportArrivalAcronym;
}