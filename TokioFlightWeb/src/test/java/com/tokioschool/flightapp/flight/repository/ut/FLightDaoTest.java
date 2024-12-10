package com.tokioschool.flightapp.flight.repository.ut;

import com.tokioschool.flightapp.flight.domain.Flight;
import com.tokioschool.flightapp.flight.domain.Resource;
import com.tokioschool.flightapp.flight.domain.STATUS_FLIGHT;
import com.tokioschool.flightapp.flight.dto.AccountCancelledFlightsBuAirportDto;
import com.tokioschool.flightapp.flight.dto.AccountFlightDto;
import com.tokioschool.flightapp.flight.repository.AirportDao;
import com.tokioschool.flightapp.flight.repository.FlightDao;
import com.tokioschool.flightapp.flight.repository.ResourceDao;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FLightDaoTest {

    @Autowired
    private AirportDao airportDao;

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private ResourceDao resourceDao;

    @BeforeEach
    void beforeEach() {
        final Resource flightImage = Resource.builder()
                .resourceId(UUID.randomUUID())
                .contentType("img/jpeg")
                .size(20)
                .fileName("testName.jpeg")
                .build();

        final Flight flight = Flight.builder()
                .occupancy(0)
                .capacity(90)
                .airportDeparture(airportDao.getReferenceById("GLA"))
                .airportArrival(airportDao.getReferenceById("BCN"))
                .number("EJU3037")
                .departureTime(LocalDateTime.now().plusSeconds(60))
                .statusFlight(STATUS_FLIGHT.SCHEDULED)
                .flightBookings(new HashSet<>())
                .flightImg(flightImage)
                .build();

        flightDao.save(flight);
    }

    @Test
    @Order(1)
    void givenFlights_whenFindAll_returnListOfFlights() {
        final List<Flight> flights = flightDao.findAll();

        assertThat(flights).isNotEmpty().hasSizeGreaterThanOrEqualTo(3);
    }

    @Test
    @Order(2)
    void givenAcronym_whenFindByAirport_returnListOfFlights(){
        final List<Flight> flights = flightDao.findAllByAirportAcronym("BCN");

        assertThat(flights).isNotEmpty().hasSizeGreaterThanOrEqualTo(3);
    }

    @Test
    @Order(3)
    void givenAcronym_whenFindByDeparture_returnListOfFlights(){
        final List<Flight> flights = flightDao.findAllByAirportDepartureAcronymContainsIgnoreCase("BCN");

        assertThat(flights).isNotEmpty().hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    @Order(4)
    void givenAcronym_whenFindByArrival_returnListOfFlights(){
        final List<Flight> flights = flightDao.findAllByAirportArrivalAcronymContainsIgnoreCase("BCN");

        assertThat(flights).isNotEmpty().hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    @Order(5)
    void givenAccountFlights_whenGetAccountFlightByAirportDeparture_returnListOfFlights(){
        final List<AccountFlightDto> flights = flightDao.getAccountFlightsByAirportDeparture();

        assertThat(flights)
                .isNotEmpty()
                .hasSize(2);

        assertThat(flights.getFirst())
                .returns("BCN",AccountFlightDto::getAirportDepartureAcronym)
                .returns(2,AccountFlightDto::getAccount);
        assertThat(flights.getLast())
                .returns("GLA",AccountFlightDto::getAirportDepartureAcronym)
                .returns(1,AccountFlightDto::getAccount);
    }

    @Test
    @Order(6)
    void givenAccountFlights_whenGetAccountFlightsByAirportArrival_returnListOfFlights(){
        final List<AccountFlightDto> flights = flightDao.getAccountFlightsByAirportArrival();

        assertThat(flights)
                .isNotEmpty()
                .hasSize(2);

        assertThat(flights.getFirst())
                .returns("BCN",AccountFlightDto::getAirportArrivalAcronym)
                .returns(1,AccountFlightDto::getAccount);

        assertThat(flights.getLast())
                .returns("GLA",AccountFlightDto::getAirportArrivalAcronym)
                .returns(2,AccountFlightDto::getAccount);
    }

    @Test
    @Order(7)
    void givenDepartureTimeAndStatus_whenFindFlightsByDepartureTimeIsAfterAndStatusFlightIs_returnListOfFlights(){
        final List<Flight> flights = flightDao.findFlightsByDepartureTimeIsAfterAndStatusFlightIs(
                        LocalDateTime.now().minusHours(2),
                        STATUS_FLIGHT.SCHEDULED);

        assertThat(flights)
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @Order(8)
    void givenNumberFlight_whenFindFlightByNumberLike_returnListOfFlights(){
        final List<Flight> flights = flightDao.findByNumberLike("BCN%");

        assertThat(flights)
                .isNotEmpty()
                .hasSize(2);
    }

    @Test
    @Order(8)
    void givenNumberFlight_whenFindFlightByContains_returnListOfFlights(){
        final List<Flight> flights = flightDao.findByNumberContains("EJ");

        assertThat(flights)
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @Order(9)
    void givenPageableFlights_whenFindAll_returnPageOfFlights(){
        final int pageSize = 1;
        final int pageNumber = 0;

        final Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(Sort.Direction.DESC,"number"));
        final Page<Flight> pageFlights = flightDao.findAll(pageable);

        assertThat(pageFlights.getTotalPages()).isEqualTo(3);
        assertThat(pageFlights.getNumber()).isZero();
        assertThat(pageFlights.getNumberOfElements()).isEqualTo(1);
        assertThat(pageFlights.getContent().getFirst().getNumber()).isEqualTo("EJU3037");
    }

    @Test
    @Order(10)
    void givenAccountFlights_whenFindAllByAirportAcronymAndStatusCancelled_returnPageOfFlights(){
        final List<AccountFlightDto> flights = flightDao.findAllByAirportAcronymAndStatusCancelled();

        assertThat(flights)
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @Order(11)
    void givenFlightNumber_whenFindAll_returnPageOfFlights(){
        final Long id = flightDao.findByNumberLike("EJU3037").getFirst().getId();
        final int rowsUpdate = flightDao.updateFlightNumber(id,"EJU0001");

        assertThat(rowsUpdate)
                .isOne();
    }

    @Test
    @Order(12)
    void givenFlightStatusCancelled_whenGetFlightDepartureCancelledCounter_returnListOfFLights (){
        final List<AccountCancelledFlightsBuAirportDto>  flightsCancelledCounterByAirport=
                flightDao.getFlightDepartureCancelledCounter(STATUS_FLIGHT.CANCELLED);

        assertThat(flightsCancelledCounterByAirport)
                .hasSize(1);
    }

    @Test
    @Order(12)
    void givenFlightStatusCancelled_whenGetFlightArrivalCancelledCounter_returnListOfFLights (){
        final List<AccountCancelledFlightsBuAirportDto>  flightCancelledCounterByAirport=
                flightDao.getFlightArrivalCancelledCounter(STATUS_FLIGHT.CANCELLED);

        assertThat(flightCancelledCounterByAirport)
                .hasSize(0);
    }
}
