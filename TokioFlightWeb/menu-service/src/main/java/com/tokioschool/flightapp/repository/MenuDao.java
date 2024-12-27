package com.tokioschool.flightapp.repository;

import com.tokioschool.flightapp.domain.Menu;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    // method query
    long countByVegetarianIsTrue();

    // method query
    List<Menu> findByMainsName(String name);
    List<Menu> findByMainsNameIgnoreCase(String name);

    // filtrando con query mongodb, donde se busca el camo mains.name sea igual al argmento de entrada "name" y
    // aplique la opcion de sea "insenitive", pormedio del caracter 'i'
    @Query(value = """
        { mains: {
            $elemMatch: {
                name: { 
                    $regex:  '?0',
                    $options:  'i'
                }       
            } }
        }
    """ )
    List<Menu> findByMainsNameCaseInsensitive(String name);

    // mehtod query
    List<Menu> findByCaloriesGreaterThan(BigDecimal calories);
}
