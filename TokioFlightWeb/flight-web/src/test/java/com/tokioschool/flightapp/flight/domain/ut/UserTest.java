package com.tokioschool.flightapp.flight.domain.ut;

import com.tokioschool.flightapp.flight.domain.Role;
import com.tokioschool.flightapp.flight.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserTest {
/*
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenUserWithRole_whenPersistUser_thenSuccess() {
        Role role = Role.builder()
                .name("Facebook")
                .build();
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = User.builder()
                .name("Facebook")
                .surname("Facebook 2")
                .password("123")
                .email("facebook@bla.com")
                .roles(roles)
                .build();

        entityManager.persistAndFlush(role);
        entityManager.persistAndFlush(user);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getRoles())
                .isNotNull()
                .isNotEmpty();
    }
*/
}