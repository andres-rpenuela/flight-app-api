package com.tokioschool.flightapp.flight.domain.ut;

import com.tokioschool.flightapp.flight.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class FlightBookingTest {
/*
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenFlightBooking_whenPersist_thenSuccess() {
        final Airport airportDeparture = entityManager.find(Airport.class,"ABC");
        final Airport airportArrival = entityManager.find(Airport.class,"EDI");

        final Flight flight = Flight.builder()
                .statusFlight(STATUS_FLIGHT.SCHEDULED)
                .number(UUID.randomUUID().toString())
                .capacity(100)
                .occupancy(1)
                .departureTime(LocalDateTime.now().plusMonths(3))
                .airportDeparture(airportDeparture)
                .airportArrival(airportArrival).build();

        final User user = User.builder()
                .name("Facebook")
                .surname("Facebook 2")
                .password("123")
                .email("facebook@bla.com")
                .build();

        final FlightBooking flightBooking = FlightBooking.builder()
                .user(user)
                .flight(flight)
                .locator(UUID.randomUUID())
                .build();

        entityManager.persist(flight);
        entityManager.persist(user);
        entityManager.persist(flightBooking);
        entityManager.flush();

        entityManager.refresh(user);
        assertThat(user.getFlightBookings()).isNotNull();
    }
*/
}