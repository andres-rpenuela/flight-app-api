package com.tokioschool.flightapp.flight.service;

import com.tokioschool.flightapp.flight.dto.FlightBookingDTO;
import com.tokioschool.flightapp.flight.exception.OverBookingException;

import java.util.Set;

public interface FlightBookingService {

    FlightBookingDTO newBookingFlight(Long flightId, String userId) throws IllegalArgumentException, OverBookingException;

    Set<FlightBookingDTO> findAllBooking();
}
