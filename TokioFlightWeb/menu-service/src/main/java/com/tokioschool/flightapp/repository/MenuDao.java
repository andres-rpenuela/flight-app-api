package com.tokioschool.flightapp.repository;

import com.tokioschool.flightapp.domain.Menu;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository // optional el uso de la anotacion
public interface MenuDao extends MongoRepository<Menu,String> {
    // method query
    List<Menu> findByVegetarianIsTrueOrderByTitle();

    @Aggregation(
            pipeline = {
                    "{ $match: { _id: '?0' }}", //fitlrado
                    "{ $set:  { mains:  { $sortArray:  { input:  '$mains', sortBy:  {name:  -1 } } } } }"  // ordenacion sobre los mains (son operaciones para conjuntos (set, maps, ...)
            }
    )
    Optional<Menu> findByIdWithMainsOrdered(String id);
}
