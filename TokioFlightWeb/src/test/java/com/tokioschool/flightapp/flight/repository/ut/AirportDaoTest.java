package com.tokioschool.flightapp.flight.repository.ut;

import com.tokioschool.flightapp.flight.domain.Airport;
import com.tokioschool.flightapp.flight.repository.AirportDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AirportDaoTest {

    @Autowired
    private AirportDao airportDao;

    @Test
    void givenAirports_whenFindAll_thenReturnListNotEmpty(){
        List<Airport> airports = airportDao.findAll();

        assertThat(airports).isNotNull().hasSizeGreaterThan(3);
    }

    @Test
    void givenAcronymUnknown_whenFindByAcronym_thenReturnEmpty(){
        Optional<Airport> airport = airportDao.findByAcronym("A");

        assertThat(airport).isEmpty();
    }
    @Test
    void givenAcronymUnknown_whenFindByAcronymLike_thenReturnNonEmpty(){
        Set<Airport> airport = airportDao.findByAcronymLike("%BC%");

        assertThat(airport).isNotEmpty();
    }
    @Test
    void givenAcronymUnknown_whenFindByAcronymContainsIgnoreCase_thenReturnEmpty(){
        Set<Airport> airport = airportDao.findByAcronymContainsIgnoreCase("BC");

        assertThat(airport).isNotEmpty();
    }
}
