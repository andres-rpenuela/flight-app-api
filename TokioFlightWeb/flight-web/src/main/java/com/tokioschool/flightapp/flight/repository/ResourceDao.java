package com.tokioschool.flightapp.flight.repository;

import com.tokioschool.flightapp.flight.domain.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResourceDao extends CrudRepository<Resource, Long> {

    Optional<Resource> findByResourceId(UUID uuid);

}
