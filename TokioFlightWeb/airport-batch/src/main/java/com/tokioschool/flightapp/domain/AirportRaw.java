package com.tokioschool.flightapp.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "airports_raw")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AirportRaw {

    @EmbeddedId
    private AirportRawId airportRawId;

    private String name;
    private BigDecimal lat;
    private BigDecimal lon;
    private String country;
}
