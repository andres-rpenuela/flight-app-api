package com.tokioschool.flightapp.flight.service.impl.ut;

import com.tokioschool.flightapp.flight.dto.FlightBookingDTO;
import com.tokioschool.flightapp.flight.dto.FlightDTO;
import com.tokioschool.flightapp.flight.dto.STATUS_FLIGHT_DTO;
import com.tokioschool.flightapp.flight.dto.session.FlightBookingSessionDTO;
import com.tokioschool.flightapp.flight.repository.FlightBookingDao;
import com.tokioschool.flightapp.flight.service.FlightBookingService;
import com.tokioschool.flightapp.flight.service.FlightService;
import com.tokioschool.flightapp.flight.service.impl.FlightBookingSessionServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightBookingSessionServiceUTest {
    @InjectMocks
    private FlightBookingSessionServiceImpl flightBookingSessionService;

    @Mock
    private FlightService flightService;

    @Spy
    private FlightBookingService flightBookingService;


    private FlightBookingSessionDTO flightBookingSessionDTO;

    @BeforeEach
    public void setUp() {
        flightBookingSessionDTO = FlightBookingSessionDTO.builder().build();
    }

    @Test
    @Order(1)
    void givenAnewBooking_whenAddFlightId_thenSuccess() {
        flightBookingSessionDTO = FlightBookingSessionDTO.builder().build();

        final FlightDTO flightDTOMock = generateFlightDTOMock();
        Mockito.when(flightService.findById(flightDTOMock.getId()))
                .thenReturn(flightDTOMock);

        flightBookingSessionService.addFlightId(flightDTOMock.getId(),
                flightBookingSessionDTO);

        assertThat(flightBookingSessionDTO)
                .returns(flightDTOMock.getId(), FlightBookingSessionDTO::getCurrentFlightId)
                .satisfies(value->assertThat(value.getDiscardedFlightIds()).isEmpty());
    }

    @Test
    @Order(2)
    void givenOtherBooking_whenAddFlightId_thenSuccess() {

        // given
        final FlightDTO flightDTOMock = generateFlightDTOMock();
        final FlightDTO flightDTOMock2 = generateFlightDTOMock();

        flightBookingSessionDTO.setCurrentFlightId(flightDTOMock2.getId());
        flightBookingSessionDTO.getDiscardedFlightIds().add(flightDTOMock.getId());

        Mockito.when(flightService.findById(flightDTOMock2.getId()))
                .thenReturn(flightDTOMock2);

        // when
        flightBookingSessionService.addFlightId(flightDTOMock2.getId(),
                flightBookingSessionDTO);

        // then
        assertThat(flightBookingSessionDTO)
                .returns(flightDTOMock2.getId(), FlightBookingSessionDTO::getCurrentFlightId)
                .returns(1,value -> value.getDiscardedFlightIds().size());
    }

    @Test
    @Order(3)
    void givenBooking_whenConfirmFlightBookingSession_thenSuccess() {

        final FlightDTO flightDTOMock = generateFlightDTOMock();

        Mockito.when(flightService.findById(flightDTOMock.getId()))
                .thenReturn(flightDTOMock);

        flightBookingSessionService.addFlightId(flightDTOMock.getId(),
                flightBookingSessionDTO);

        flightBookingSessionService.confirmFlightBookingSession(flightBookingSessionDTO);

        Mockito.verify(flightBookingService,Mockito.times(1)).newBookingFlight(Mockito.eq(flightDTOMock.getId()),Mockito.anyString());
    }

    @Test
    @Order(4)
    void givenDataBookingSession_whenGetFlightsById_thenSuccess() {
        final FlightDTO flightDTOMock = generateFlightDTOMock();

        Mockito.when(flightService.findById(flightDTOMock.getId()))
                .thenReturn(flightDTOMock);

        Mockito.when(flightService.getFlightsId(Mockito.any()))
                .thenReturn(generateFlightMapDtoMock(flightDTOMock));

        flightBookingSessionService.addFlightId(flightDTOMock.getId(),
                flightBookingSessionDTO);

        Map<Long,FlightDTO> flightDTOMap = flightBookingSessionService.getFlightsById(flightBookingSessionDTO);

        assertThat(flightDTOMap).isNotEmpty();
    }

    private FlightDTO generateFlightDTOMock(){
        final Random random = new Random();
        return FlightDTO.builder()
                .id(random.nextLong())
                .status(STATUS_FLIGHT_DTO.SCHEDULED)
                .number("0001")
                .airportDepartureAcronym("BCA")
                .airportDepartureAcronym("GLA")
                .capacity(10)
                .occupancy(0)
                .departureTime(LocalDateTime.of(2024,12,22,0,0,0))
                .build();
    }

    private Map<Long,FlightDTO> generateFlightMapDtoMock(FlightDTO flightDTO){


        Map<Long,FlightDTO> flightDTOMap = new HashMap<>();
        flightDTOMap.put(flightDTO.getId(),flightDTO);
        return flightDTOMap;
    }


}