package com.tokioschool.flightapp.csv;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Value
@Builder
public class AirportCsv {

    Long id;
    String ident;
    AirportType type;
    String name;
    BigDecimal latitudeDeg;
    BigDecimal longitudeDeg;
    Integer elevationFt;
    String continent;
    String isoCountry;
    String isoRegion;
    String municipality;
    String scheduledService;
    String gpsCode;
    String iataCode;
    String localCode;
    String homeLink;
    String wikipediaLink;
    List<String> keywords;


    @AllArgsConstructor
    public enum AirportType {
        CLOSED_AIRPORT("closed"),
        BALLOON_PORT("balloonport"),
        HELIPORT("heliport"),
        LARGE_AIRPORT("large_airport"),
        MEDIUM_AIRPORT("medium_airport"),
        SEAPLANE_BASE("seaplane_base"),
        SMALL_AIRPORT("small_airport");

        @Getter private final String label;

        public static AirportType ofLabel(String label){
            return Arrays.stream(AirportType.values())
                    .filter(airportType -> airportType.getLabel().toLowerCase().equals(label))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No AiportType with label: %s".formatted(label)));
        }
    }
}