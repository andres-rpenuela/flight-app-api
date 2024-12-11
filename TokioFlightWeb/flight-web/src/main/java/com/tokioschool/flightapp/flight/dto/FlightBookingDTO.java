package com.tokioschool.flightapp.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class FlightBookingDTO {

    private String locator; // localizador de reserva
}