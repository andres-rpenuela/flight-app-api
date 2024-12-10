package com.tokioschool.flightapp.flight.domain.ut;

import com.tokioschool.flightapp.flight.domain.Airport;
import com.tokioschool.flightapp.flight.domain.Flight;
import com.tokioschool.flightapp.flight.domain.STATUS_FLIGHT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class FlightUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenFlight_whenPersist_thenSuccess() {
        final Airport airportDeparture = entityManager.find(Airport.class,"ABC");
        final Airport airportArrival = entityManager.find(Airport.class,"EDI");

        Flight flight = Flight.builder()
                .statusFlight(STATUS_FLIGHT.SCHEDULED)
                .number(UUID.randomUUID().toString())
                .capacity(100)
                .occupancy(1)
                .departureTime(LocalDateTime.now().plusMonths(3))
                .airportDeparture(airportDeparture)
                .airportArrival(airportArrival).build();

        flight = entityManager.persistAndFlush(flight);

        assertThat(flight.getId()).isNotNull();
    }

}