package com.tokioschool.flightapp.base.transaccions.repository;

import com.tokioschool.flightapp.base.transaccions.domain.Base;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao extends CrudRepository<Base,Long> {
}
