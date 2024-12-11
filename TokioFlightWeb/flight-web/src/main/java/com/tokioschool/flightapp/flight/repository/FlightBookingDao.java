package com.tokioschool.flightapp.flight.repository;

import com.tokioschool.flightapp.flight.domain.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlightBookingDao extends JpaRepository<FlightBooking,Long> {

    List<FlightBooking> getFlightBookingByFlightId(Long flightId);
    Optional<FlightBooking> findFLightBookingByLocator(UUID bookingLocator);
    List<FlightBooking> findAll();
}
