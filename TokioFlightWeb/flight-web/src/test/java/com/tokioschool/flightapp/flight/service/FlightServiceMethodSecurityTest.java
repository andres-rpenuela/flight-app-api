package com.tokioschool.flightapp.flight.service;

import com.tokioschool.flightapp.flight.dto.FlightDTO;
import com.tokioschool.flightapp.flight.repository.AirportDao;
import com.tokioschool.flightapp.flight.repository.FlightDao;
import com.tokioschool.flightapp.flight.repository.ResourceDao;
import com.tokioschool.flightapp.flight.service.impl.FlightServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) // Obtener parte del contexto de Spring
@ContextConfiguration(classes = { // Compoentes que se quieren añadir en el contexto
        FlightServiceImpl.class,
        FlightServiceMethodSecurityTest.MockFlightServiceImplConfiguration.class
})
@EnableMethodSecurity // Habilitar en el contexto los medotods de seguridad por anotación
@ActiveProfiles("test")
class FlightServiceMethodSecurityTest {

    // Inyecion + Mock de Fligh service
    @MockitoSpyBean private FlightService flightService;

    @Test
    void givenNonUser_whenCreateFlight_thenAuthenticationCredentialsNotFoundException() {
        Assertions.assertThatThrownBy(() -> flightService.createFlight(null,null))
                .isInstanceOf(AuthenticationCredentialsNotFoundException.class);
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_whenCreateFlight_thenAccessDeniedException() {
        Assertions.assertThatThrownBy(() -> flightService.createFlight(null,null))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockUser(username = "user@bla.com",authorities = {"USER"})
    void givenUserNoAuthorized_whenCreateFlight_thenAccessDeniedException() {
        Assertions.assertThatThrownBy(() -> flightService.createFlight(null,null))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockUser(username = "user@bla.com",authorities = {"ADMIN"})
    void givenUserAuthorized_whenCreateFlight_thenAccessDeniedException() {
        Mockito.doReturn(null).when(flightService).createFlight(Mockito.any(),Mockito.any());

        FlightDTO flightDTO = flightService.createFlight(null,null);

        Assertions.assertThat(flightDTO).isNull();
    }

    /**
     * Depednecias moquedas usadas en Flight service
     */
    @Configuration
    public static class MockFlightServiceImplConfiguration {

        @Bean
        FlightDao flightDao() {
            return Mockito.mock(FlightDao.class);
        }

        @Bean
        AirportDao airportDao() {
            return Mockito.mock(AirportDao.class);
        }

        @Bean
        ResourceService resourceService() {
            return Mockito.mock(ResourceService.class);
        }

        @Bean
        ModelMapper modelMapper() {
            return Mockito.mock(ModelMapper.class);
        }

        @Bean
        ResourceDao  resourceDao() {
            return Mockito.mock(ResourceDao.class);
        }
    }
}