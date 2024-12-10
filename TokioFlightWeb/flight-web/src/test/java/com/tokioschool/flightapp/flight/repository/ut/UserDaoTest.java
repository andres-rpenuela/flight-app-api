package com.tokioschool.flightapp.flight.repository.ut;

import com.tokioschool.flightapp.flight.domain.Role;
import com.tokioschool.flightapp.flight.domain.User;
import com.tokioschool.flightapp.flight.repository.RoleDao;
import com.tokioschool.flightapp.flight.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserDaoTest {
/*
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Test
    void givenUsers_whenFindAll_thenReturnListNotEmpty(){
        User user = userDao.findByEmail("user@bla.com").orElse(null);

        assertThat(user).isNotNull();
    }

    @Test
    void giveNewUserWithRoles_whenCreate_thenReturnPersistSuccess(){
        final List<Role> roles = (List<Role>) roleDao.findAll();
        User user = User.builder()
                .name("andres")
                .surname("rpenuela")
                .email("andres.rpenuela@bla.com")
                .password("$2a$12$zNe45Ch0yDeeMoBb0B9X4OSoKuzj5pQAt2LY7VN5F7vq2U9751hna") //pwd: user, https://bcrypt-generator.com/
                .roles(new HashSet<>(roles))
                .build();

        userDao.save(user);
        userDao.flush();;
        assertThat(user)
                .returns("andres",User::getName)
                .satisfies(element -> assertThat(element.getCreated()).isNotNull())
                .satisfies(element -> assertThat(element.getId()).isNotNull())
                .satisfies(element -> assertThat(element.getRoles().stream().map(Role::getName).toList())
                        .isNotNull()
                        .isNotEmpty()
                        .containsExactlyInAnyOrder("USER","ADMIN"));
    }

    @Test
    void givenUser_whenDeleted_theManyToManyIsDeleted(){
        final User user = userDao.findByEmail("user@bla.com").get();

        userDao.delete(user);

        List<User> users = userDao.findAll();

        assertThat(users).doesNotContain(user).hasSizeGreaterThanOrEqualTo(1);
    }
 */
}
