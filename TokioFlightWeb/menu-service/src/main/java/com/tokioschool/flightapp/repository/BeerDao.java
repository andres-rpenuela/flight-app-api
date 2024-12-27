package com.tokioschool.flightapp.repository;

import com.tokioschool.flightapp.domain.Beer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface BeerDao extends MongoRepository<Beer, UUID> {
}
