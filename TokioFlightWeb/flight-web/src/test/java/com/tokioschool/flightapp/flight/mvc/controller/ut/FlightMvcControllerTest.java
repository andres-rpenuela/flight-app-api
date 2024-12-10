package com.tokioschool.flightapp.flight.mvc.controller.ut;

import com.tokioschool.flightapp.flight.dto.FlightDTO;
import com.tokioschool.flightapp.flight.mvc.controller.FlightMvcController;
import com.tokioschool.flightapp.flight.service.AirportService;
import com.tokioschool.flightapp.flight.service.FlightService;
import com.tokioschool.flightapp.flight.validation.binding.FlightMvcDTOValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(FlightMvcController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class FlightMvcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @MockBean
    private AirportService airportService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private FlightMvcDTOValidator flightMvcDTOValidator;
    @Test
    void givenRequestAllFlights_whenListFlightsHandler_returnResponseSuccess() throws Exception {

        BDDMockito.given(flightService.findAllFlights()).willReturn(List.of());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/flight/flights"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.model().attribute("flights", Matchers.empty()))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void givenRequestEditOrCreateFlight_whenEditFlightHandler_returnResponseSuccess() throws Exception {

        BDDMockito.given(flightService.findById(Mockito.any(Long.class))).willReturn(FlightDTO.builder().build());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/flight/flight-edit"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                //.andExpect(MockMvcResultMatchers.model().attribute("flight", Matchers.notNullValue()))
                .andReturn().getResponse().getContentAsString();
    }
}