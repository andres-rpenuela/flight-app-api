package com.tokioschool.flightapp.domain;

import jakarta.persistence.Column;
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

    //@Column(precision = 10, scale = 8)
    private BigDecimal lat;

    //@Column(precision = 10, scale = 8)
    private BigDecimal lon;

    private String country;
}
