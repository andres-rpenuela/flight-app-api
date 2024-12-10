package com.tokioschool.flightapp.flight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirportDTO {

    private String acronym;
    private String name;
    private String country;
}
