package com.tokioschool.flightapp.repository;

import com.tokioschool.flightapp.domain.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository // optional el uso de la anotacion
public interface MenuDao extends MongoRepository<Menu,String> {
}
