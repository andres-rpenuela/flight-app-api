package com.tokioschool.flightapp.flight.repository.ut;

import com.tokioschool.flightapp.flight.domain.Role;
import com.tokioschool.flightapp.flight.repository.RoleDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Test
    void givenRoles_whenFindAll_thenReturnListNotEmpty(){
        final List<Role> roles = (List<Role>) roleDao.findAll();

        assertThat(roles).isNotNull().hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void givenRole_whenDelete_thenDataIntegrityViolation(){
        final Role role = roleDao.findByNameIgnoreCase("admin");

        Assertions.assertThrows(DataIntegrityViolationException.class, ()->{
            roleDao.delete(role);
            roleDao.findAll();
        });
    }
}
