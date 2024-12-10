package com.tokioschool.flightapp.flight.service.impl;

import com.tokioschool.flightapp.flight.dto.FlightBookingDTO;
import com.tokioschool.flightapp.flight.dto.FlightDTO;
import com.tokioschool.flightapp.flight.dto.UserDTO;
import com.tokioschool.flightapp.flight.dto.session.FlightBookingSessionDTO;
import com.tokioschool.flightapp.flight.exception.OverBookingException;
import com.tokioschool.flightapp.flight.service.FlightBookingService;
import com.tokioschool.flightapp.flight.service.FlightBookingSessionService;
import com.tokioschool.flightapp.flight.service.FlightService;
import com.tokioschool.flightapp.flight.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightBookingSessionServiceImpl implements FlightBookingSessionService {

    private final FlightService flightService;
    private final FlightBookingService flightBookingService;
    private final UserService userService;


    @Override
    public void addFlightId(Long flightId, FlightBookingSessionDTO flightBookingSessionDTO) {
        try {
            final FlightDTO flightDTO = flightService.findById(flightId);
            Optional.ofNullable(flightBookingSessionDTO.getCurrentFlightId())
                    .ifPresent(flightBookingSessionDTO.getDiscardedFlightIds()::add); // add the discarded flight id

            flightBookingSessionDTO.setCurrentFlightId(flightDTO.getId());

            // remove if exit in the discarded
            flightBookingSessionDTO.getDiscardedFlightIds().remove(flightDTO.getId());
        }catch (IllegalArgumentException e){
            log.error("Error while adding flight id {}, cause", flightId,e);
        }
    }

    @Override
    public FlightBookingDTO confirmFlightBookingSession(FlightBookingSessionDTO flightBookingSessionDTO) {
        try {
            final FlightDTO flightDTO = flightService.findById(flightBookingSessionDTO.getCurrentFlightId());

            // se obtiente  el context del usuario
            final String emailUser = SecurityContextHolder.getContext().getAuthentication().getName();
            final UserDTO userDTO = userService.findByEmail(emailUser)
                    .orElseThrow(() -> new IllegalArgumentException("User with username:%s not found.".formatted(emailUser)));

            return flightBookingService.newBookingFlight(flightDTO.getId(), userDTO.getId());
        }catch (IllegalArgumentException | OverBookingException e){
            log.error("Error while confirm flight booking, flight id {}, cause", flightBookingSessionDTO.getCurrentFlightId(),e);
        }

        return null;
    }

    @Override
    public Map<Long, FlightDTO> getFlightsById(FlightBookingSessionDTO flightBookingSessionDTO) {
        HashSet<Long> flightsIds = new HashSet<>(flightBookingSessionDTO.getDiscardedFlightIds());

        Optional.ofNullable(flightBookingSessionDTO.getCurrentFlightId()).ifPresent(flightsIds::add);

        Map<Long,FlightDTO> flightDTOMap = flightService.getFlightsId(flightsIds);

        for(Long flightId : flightsIds) {
            if( !flightDTOMap.containsKey(flightId)){
                throw new IllegalArgumentException("Flight with id %s not found! ".formatted(flightId));
            }
        }
        return flightDTOMap;
    }
}
