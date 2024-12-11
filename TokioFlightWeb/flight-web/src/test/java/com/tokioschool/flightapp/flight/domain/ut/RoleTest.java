package com.tokioschool.flightapp.flight.domain.ut;

import com.tokioschool.flightapp.flight.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class RoleTest {
/*
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenRole_whenPersist_thenSuccess() {
        Role role = Role.builder()
                .name("Facebook")
                .build();

        entityManager.persistAndFlush(role);
        assertThat(role.getId()).isNotNull();
    }
 */
}