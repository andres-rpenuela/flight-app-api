package com.tokioschool.flightapp.repository;

import com.tokioschool.flightapp.domain.Beer;
import com.tokioschool.flightapp.projection.BeerStyleCountAggregate;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface BeerDao extends MongoRepository<Beer, UUID> {

    @Aggregation(
            pipeline = {
                    // consideramos 'style' como campo único meidnate _id del grupo, y suma desde 1 y vuelva en 'count'
                    "{ $group : { '_id' :  '$style', 'count' :  { '$sum' :  1 } } }",
                    "{ $project :  { '_id' :  null, 'style' :  '$_id', 'count' :  '$count' } }",
                    "{ $sort : {'count' :  -1} }"
            }
    )
    List<BeerStyleCountAggregate> countByStyle();

    List<Beer> findByStyleIgnoreCase(String style);
    List<Beer> findByStyleIsNotIgnoreCase(String style);
}
