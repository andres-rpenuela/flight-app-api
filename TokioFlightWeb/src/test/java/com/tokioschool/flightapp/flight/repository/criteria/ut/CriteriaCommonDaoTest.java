package com.tokioschool.flightapp.flight.repository.criteria.ut;

import com.tokioschool.flightapp.flight.domain.Flight;
import com.tokioschool.flightapp.flight.domain.User;
import com.tokioschool.flightapp.flight.repository.criteria.CriteriaCommonDao;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CriteriaCommonDaoTest {

    @Autowired
    private EntityManager em;

    private CriteriaCommonDao criteriaCommonDao;

    @BeforeEach
    public void setUp() {
        criteriaCommonDao = new CriteriaCommonDao(em);
    }

    @Test
    @Order(1)
    void givenUserId_whenExitsByField_thenReturnTrue() {
        final boolean result = criteriaCommonDao
                .exitsByField("id","0AVRSA787897A", User.class);

        assertThat(result).isTrue();
    }

    @Test
    @Order(2)
    void givenUserId_whenExitsByField_thenReturnFalse() {
        final boolean result = criteriaCommonDao
                .exitsByField("id","1112", User.class);

        assertThat(result).isFalse();
    }

    @Test
    @Order(3)
    void givenFlightId_whenExitsByField_thenReturnTrue() {
        final boolean result = criteriaCommonDao
                .exitsByField("id",1, Flight.class);

        assertThat(result).isTrue();
    }
    @Test
    @Order(4)
    void givenFlightId_whenExitsByField_thenReturnFalse() {
        final boolean result = criteriaCommonDao
                .exitsByField("id",1000, Flight.class);

        assertThat(result).isFalse();
    }

    @Test
    @Order(5)
    void givenUserId_whenExitsByField_thenReturnElement() {
        final Optional<User> maybeUser = criteriaCommonDao
                .getRowByField("id","0AVRSA787897A", User.class);

        assertThat(maybeUser).isPresent()
                .get()
                .returns("0AVRSA787897A",User::getId);
    }

    @Test
    @Order(5)
    void givenUserId_whenExitsByField_thenReturnEmpaty() {
        final Optional<User> maybeUser = criteriaCommonDao
                .getRowByField("id","122", User.class);

        assertThat(maybeUser).isEmpty();
    }
}