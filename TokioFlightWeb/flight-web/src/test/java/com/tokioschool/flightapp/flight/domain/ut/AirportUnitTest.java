package com.tokioschool.flightapp.flight.domain.ut;

import com.tokioschool.flightapp.flight.domain.Airport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AirportUnitTest {
/*
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenAirport_whenPersist_thenSuccess() {
        Airport airport = Airport.builder()
                .acronym("TES")
                .name("Facebook")
                .country("Spain")
                .latitude(BigDecimal.ONE)
                .longitude(BigDecimal.TWO)
                .build();
        airport = entityManager.persistAndFlush(airport);

        assertThat(airport.getName()).isEqualTo("Facebook");
    }
*/
}