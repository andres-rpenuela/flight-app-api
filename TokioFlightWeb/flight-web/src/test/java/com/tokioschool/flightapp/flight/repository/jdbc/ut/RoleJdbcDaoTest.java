package com.tokioschool.flightapp.flight.repository.jdbc.ut;

import com.tokioschool.flightapp.flight.domain.Role;
import com.tokioschool.flightapp.flight.repository.jdcb.RoleJdbcDao;
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
class RoleJdbcDaoTest {
/*
    private RoleJdbcDao roleJdbcDao;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        // dado que RoleJdbcDao no es un repository, se debe
        // crear como un test normal
        roleJdbcDao = new RoleJdbcDao(namedParameterJdbcTemplate,jdbcTemplate);
    }

    @Test
    @Order(1)
    void givenRoles_whenCountRoles_thenReturnCount() {
        final int numberRoles = roleJdbcDao.countRoles();

        assertThat(numberRoles).isEqualTo(2);
    }

    @Test
    @Order(2)
    void givenRoleNew_whenInsertNewRole_thenReturnId() {
        final Role role = Role.builder().name("dev").build();

        final Long id = roleJdbcDao.newRole(role);

        assertThat(id).isNotNull().isGreaterThan(0);
    }

    @Test
    @Order(3)
    void givenRoleId_whenFindRoleById_thenReturnRole() {
        final Optional<Role> role = roleJdbcDao.findRoleById(1L);

        assertThat(role).isPresent();
        assertThat(role.get().getName()).isEqualTo("USER");
        assertThat(role.get().getId()).isEqualTo(1L);
    }

    @Test
    @Order(4)
    void givenRoleName_whenDeleteRoleByName_thenOperationSuccess() {
        final Role role = Role.builder().name("dev").build();

        roleJdbcDao.newRole(role);

        final int rowsAffected = roleJdbcDao.deleteRoleByName("dev");

        assertThat(rowsAffected).isOne();
    }

    @Test
    @Order(5)
    void givenSQLInjection_whenDeleteRoleByName_thenOperationSuccess() {
        final Role role = Role.builder().name("dev").build();

        roleJdbcDao.newRole(role);

        // un user puedo modificar esto por "dev" por "dev' OR '1' = '1", borra todos los elementos
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> roleJdbcDao.deleteRoleByName("dev' OR '1' = '1") );
    }

    @Test
    @Order(6)
    void givenRoleIdUnknown_whenFindRoleById_thenReturnEmpty() {
        final Optional<Role> role = roleJdbcDao.findRoleById(3L);

        assertThat(role).isEmpty();
    }
*/
}
