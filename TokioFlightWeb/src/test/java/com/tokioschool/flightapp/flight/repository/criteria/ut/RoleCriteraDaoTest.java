package com.tokioschool.flightapp.flight.repository.criteria.ut;

import com.tokioschool.flightapp.flight.domain.Role;
import com.tokioschool.flightapp.flight.repository.criteria.RoleCriteriaDao;
import com.tokioschool.flightapp.flight.repository.jdcb.RoleJdbcDao;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleCriteraDaoTest {

    private RoleCriteriaDao roleCriteriaDao;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        // dado que RoleJdbcDao no es un repository, se debe
        // crear como un test normal
        roleCriteriaDao = new RoleCriteriaDao(em);
    }

    @Test
    @Order(1)
    void givenName_whenFindByName_thenReturnRole() {
        final Optional<Role> maybeRole = roleCriteriaDao.findRoleByName("USER");

        assertThat(maybeRole).isPresent();
        assertThat(maybeRole.get())
                .returns(1L,Role::getId)
                .returns("USER",Role::getName);

    }

    @Test
    @Order(2)
    void givenNameUnknown_whenFindByName_thenReturnEmpty() {
        final Optional<Role> maybeRole = roleCriteriaDao.findRoleByName("dev");

        assertThat(maybeRole).isEmpty();
    }
}
