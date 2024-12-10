package com.tokioschool.flightapp.flight.service;

import com.tokioschool.flightapp.flight.dto.FlightBookingDTO;
import com.tokioschool.flightapp.flight.dto.FlightDTO;
import com.tokioschool.flightapp.flight.dto.session.FlightBookingSessionDTO;

import java.util.Map;

public interface FlightBookingSessionService {

    void addFlightId(Long flightId, FlightBookingSessionDTO flightBookingSessionDTO);
    FlightBookingDTO confirmFlightBookingSession(FlightBookingSessionDTO flightBookingSessionDTO);
    Map<Long, FlightDTO> getFlightsById(FlightBookingSessionDTO flightBookingSessionDTO);
}
