package com.tokioschool.flightapp.flight.service.impl;

import com.tokioschool.flightapp.flight.domain.Flight;
import com.tokioschool.flightapp.flight.domain.FlightBooking;
import com.tokioschool.flightapp.flight.domain.User;
import com.tokioschool.flightapp.flight.dto.FlightBookingDTO;
import com.tokioschool.flightapp.flight.exception.OverBookingException;
import com.tokioschool.flightapp.flight.repository.FlightBookingDao;
import com.tokioschool.flightapp.flight.repository.FlightDao;
import com.tokioschool.flightapp.flight.repository.UserDao;
import com.tokioschool.flightapp.flight.service.FlightBookingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightBookingServiceImpl implements FlightBookingService {

    private final FlightDao flightDao;
    private final UserDao userDao;
    private final FlightBookingDao flightBookingDao;

    private final ModelMapper modelMapper;

    @Override
    @Transactional // aplica @version en Flight, locking optimistic > no bloque el acceso solo la modificiaon si cambia la version
    //@Transactional(isolation = Isolation.SERIALIZABLE) // locking pessimic > bloquea el registro y no puede ser uitlizado por otra query
    public FlightBookingDTO newBookingFlight(Long flightId, String userId) throws IllegalArgumentException, OverBookingException {
        final Flight flight = flightDao.findById(flightId)
                .orElseThrow(()-> new IllegalArgumentException("Flight with id: %s not found!".formatted(flightId)));

        final User user = userDao.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("User with id: %s not found!".formatted(userId)));

        if(flight.getOccupancy() >= flight.getCapacity()){
            throw new OverBookingException("Flight with id:%s without free places".formatted(flightId));
        }


        final FlightBooking flightBooking = FlightBooking.builder()
                .locator(UUID.randomUUID())
                .flight(flight)
                .user(user).build();

        flight.setOccupancy(flight.getOccupancy() + 1);

        if(Objects.isNull(flight.getFlightBookings())){
            flight.setFlightBookings(new HashSet<>());
        }
        flight.getFlightBookings().add(flightBooking);

        flightDao.save(flight);

        return modelMapper.map(flightBooking, FlightBookingDTO.class);
    }

    public Set<FlightBookingDTO> findAllBooking(){

        return flightBookingDao.findAll().stream()
                .map(flightBooking -> modelMapper.map(flightBooking, FlightBookingDTO.class))
                .collect(Collectors.toSet());
    }

}
