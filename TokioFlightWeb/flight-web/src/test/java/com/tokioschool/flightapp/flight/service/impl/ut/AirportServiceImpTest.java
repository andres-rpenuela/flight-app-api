package com.tokioschool.flightapp.flight.service.impl.ut;

import com.tokioschool.flightapp.flight.configuration.ModelMapperBeanConfiguration;
import com.tokioschool.flightapp.flight.configuration.mapper.ResourceMapperConfiguration;
import com.tokioschool.flightapp.flight.domain.Airport;
import com.tokioschool.flightapp.flight.domain.Flight;
import com.tokioschool.flightapp.flight.dto.AirportDTO;
import com.tokioschool.flightapp.flight.dto.FlightDTO;
import com.tokioschool.flightapp.flight.repository.AirportDao;
import com.tokioschool.flightapp.flight.service.impl.AirportServiceImp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AirportServiceImpTest {

    @InjectMocks
    private AirportServiceImp airportService;

    @Mock
    private AirportDao airportDao;
    @Mock
    private ModelMapper modelMapper;

    private static List<Airport> airportsMock = List.of();

    @BeforeAll
    public static void beforeAll() {
        Airport bca = Airport.builder().acronym("BCN").name("Barcelona airport").country("Spain").build();
        Airport gla = Airport.builder().acronym("GLA").name("Glasgom airport").country("United Kingdom").build();
        airportsMock = Arrays.asList(bca,gla);
    }
    @Test
    void givenAirport_whenGetAllAirports_thenReturnAirports() {
        Mockito.when(airportDao.findAll()).thenReturn(airportsMock);
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.eq(AirportDTO.class)))
                .then(invocationOnMock -> mapperAirportToAirportDTOMock((Airport) invocationOnMock.getArguments()[0]));

        final List<AirportDTO> airportsDTO = airportService.getAllAirports();

        assertThat(airportsDTO).isNotEmpty()
                .first()
                .returns(airportsMock.getFirst().getAcronym(), AirportDTO::getAcronym)
                .returns(airportsMock.getFirst().getName(), AirportDTO::getName)
                .returns(airportsMock.getFirst().getCountry(), AirportDTO::getCountry);
    }

    /** mocks data **/
    private AirportDTO mapperAirportToAirportDTOMock(Airport airport){
        final ModelMapper mapper = new ModelMapperBeanConfiguration().modelMapper();
        new ResourceMapperConfiguration(mapper);

        return  mapper.map(airport,AirportDTO.class);
    }
}