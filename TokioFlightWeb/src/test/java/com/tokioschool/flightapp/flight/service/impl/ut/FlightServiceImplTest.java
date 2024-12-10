package com.tokioschool.flightapp.flight.service.impl.ut;

import com.tokioschool.flightapp.flight.configuration.ModelMapperBeanConfiguration;
import com.tokioschool.flightapp.flight.configuration.mapper.ResourceMapperConfiguration;
import com.tokioschool.flightapp.flight.domain.Airport;
import com.tokioschool.flightapp.flight.domain.Flight;
import com.tokioschool.flightapp.flight.domain.STATUS_FLIGHT;
import com.tokioschool.flightapp.flight.dto.FlightDTO;
import com.tokioschool.flightapp.flight.repository.FlightDao;
import com.tokioschool.flightapp.flight.service.impl.FlightServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FlightServiceImplTest {

    @InjectMocks
    private FlightServiceImpl flightService;

    @Mock
    private FlightDao flightDao;

    @Mock
    private ModelMapper modelMapper;


    private static List<Airport> airportsMock = List.of();
    private static List<Flight> flightsMock = List.of();

    @BeforeAll
    public static void beforeAll(){
        Airport bca = Airport.builder().acronym("BCN").name("Barcelona airport").country("Spain").build();
        Airport gla = Airport.builder().acronym("GLA").name("Glasgom airport").country("United Kingdom").build();
        airportsMock = Arrays.asList(bca,gla);

        Flight flight1 = Flight.builder()
                .occupancy(0)
                .capacity(90)
                .airportDeparture(bca)
                .airportArrival(gla)
                .number("EJU3837")
                .departureTime(LocalDateTime.now().plusSeconds(60))
                .statusFlight(STATUS_FLIGHT.SCHEDULED)
                .flightBookings(new HashSet<>())
                .build();
        flight1.setFlightImg(null);

        Flight flight2 = Flight.builder()
                .occupancy(0)
                .capacity(90)
                .airportDeparture(bca)
                .airportArrival(gla)
                .number("EJU3837")
                .departureTime(LocalDateTime.now().plusSeconds(60))
                .statusFlight(STATUS_FLIGHT.CANCELLED)
                .flightBookings(new HashSet<>())
                .build();

        Flight flight3 = Flight.builder()
                .occupancy(0)
                .capacity(90)
                .airportDeparture(bca)
                .airportArrival(gla)
                .number("BLA3837")
                .departureTime(LocalDateTime.now().plusSeconds(60))
                .statusFlight(STATUS_FLIGHT.SCHEDULED)
                .flightBookings(new HashSet<>())
                .build();
        flightsMock =  List.of(flight1,flight2,flight3);
    }

    @Test
    void givenFlights_whenFindAllFlights_thenReturnListFlights() {
        Mockito.when(flightDao.findAll()).thenReturn(recoverFlightsMock());
        Mockito.when(modelMapper.map(Mockito.any(),Mockito.eq(FlightDTO.class)))
                .then(invocationOnMock -> mapperFlightToFlightDTOMock((Flight) invocationOnMock.getArguments()[0]));

        List<FlightDTO> flightsDTO = flightService.findAllFlights();

        assertThat(flightsDTO).isNotNull()
                .isNotEmpty()
                .hasSize(flightsMock.size())
                .satisfies(FlightDTO.class::isInstance)
                .first()
                .returns("EJU3837",FlightDTO::getNumber)
                .satisfies(flightDTO -> Objects.isNull(flightDTO.getFlightImg()));
    }

    @Test
    void givenFlights_whenFindAllFlight_thenReturnPageFlightsUnsorted(){
        final int pageSize = 1;
        final int pageNumber = 0;

        Mockito.when(flightDao.findAll(Mockito.any(Pageable.class)))
                .thenReturn(recoverFlightPageMock(pageNumber,pageSize));
        Mockito.when(modelMapper.map(Mockito.any(),Mockito.eq(FlightDTO.class)))
                .then(invocationOnMock -> mapperFlightToFlightDTOMock((Flight) invocationOnMock.getArguments()[0]));


        final Page<FlightDTO> flightDTOPage = flightService.findAllFlights(pageSize,pageNumber);

        assertThat(flightDTOPage).isNotEmpty()
                .first()
                .returns(flightsMock.getFirst().getNumber(),FlightDTO::getNumber)
                .satisfies(flightDTO -> Objects.isNull(flightDTO.getFlightImg()));
    }


    private static final String propertySort = "number";
    private static final boolean isAsc = true;
    @Test
    void givenFlights_whenFindAllFlightSortField_thenReturnPageFlights(){
        final int pageSize = 1;
        final int pageNumber = 0;

        Mockito.when(flightDao.findAll(Mockito.any(Pageable.class)))
                .thenReturn(recoverFlightPageSortByNumberAscMock(pageNumber,pageSize));
        Mockito.when(modelMapper.map(Mockito.any(),Mockito.eq(FlightDTO.class)))
                .then(invocationOnMock -> mapperFlightToFlightDTOMock((Flight) invocationOnMock.getArguments()[0]));


        final Page<FlightDTO> flightDTOPage = flightService.findAllFlightsSortField(pageSize,pageNumber,propertySort,isAsc);

        assertThat(flightDTOPage).isNotEmpty()
                .first()
                .returns("BLA3837",FlightDTO::getNumber)
                .satisfies(flightDTO -> Objects.isNull(flightDTO.getFlightImg()));;
    }

    @Test
    void givenBadPageSize_whenFindAllFlightSortField_thenReturnPageEmpty(){
        final int pageSize = 0;
        final int pageNumber = 0;

        final Page<FlightDTO> flightDTOPage = flightService.findAllFlightsSortField(pageSize,pageNumber,propertySort,isAsc);

        assertThat(flightDTOPage).isEmpty();
    }


    /** mocks data **/
    private FlightDTO mapperFlightToFlightDTOMock(Flight flight){
        final ModelMapper mapper = new ModelMapperBeanConfiguration().modelMapper();
        new ResourceMapperConfiguration(mapper);

        return  mapper.map(flight,FlightDTO.class);
    }

    private List<Flight> recoverFlightsMock(){
        return flightsMock;
    }

    /**
     * Create a Page from List
     *
     * @param pageNumber
     * @param pageSize
     * @return page
     */
    private Page<Flight> recoverFlightPageMock(int pageNumber, int pageSize){
        final Pageable pageable = PageRequest.of(pageNumber,pageSize);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), flightsMock.size());

        List<Flight> pageContent = flightsMock.subList(start, end);
        return new PageImpl<>(pageContent, pageable, flightsMock.size());
    }

    /**
     * Create a Page from List short by number ascending
     *
     * @param pageNumber
     * @param pageSize
     * @return page
     */
    private Page<Flight> recoverFlightPageSortByNumberAscMock(int pageNumber, int pageSize){
        final Pageable pageable = PageRequest.of(pageNumber,pageSize);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), flightsMock.size());

        List<Flight> copyFlights = new ArrayList<>();
        copyFlights.addAll(flightsMock);
        Collections.sort(copyFlights,Comparator.comparing(Flight::getNumber));

        final List<Flight> pageContent = copyFlights.subList(start, end);
        return new PageImpl<>(pageContent, pageable, copyFlights.size());
    }
}