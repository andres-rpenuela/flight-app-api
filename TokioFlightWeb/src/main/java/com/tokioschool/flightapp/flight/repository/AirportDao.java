package com.tokioschool.flightapp.flight.repository;

import com.tokioschool.flightapp.flight.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AirportDao extends JpaRepository<Airport,String> {
    // query methods
    Optional<Airport> findByAcronym(String acronym);
    Set<Airport> findByAcronymLike(String acronym);
    Set<Airport> findByAcronymContainsIgnoreCase(String acronym);

}
