package com.tokioschool.flightapp.flight.repository.ut;

import com.tokioschool.flightapp.flight.domain.Role;
import com.tokioschool.flightapp.flight.domain.User;
import com.tokioschool.flightapp.flight.repository.RoleDao;
import com.tokioschool.flightapp.flight.repository.UserDao;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Transactional(propagation = Propagation.NOT_SUPPORTED) // No hace rollback automaticao
class UserDaoNoTransactionaAndLazylTest {
/*
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void givenUsers_whenFindAll_thenReturnListNotEmpty(){
        User user = userDao.findByEmail("user@bla.com").orElse(null);

        assertThat(user).isNotNull();
    }

    @BeforeEach
    void beforeEach(){
        final List<Role> roles = (List<Role>) roleDao.findAll();
        User user = User.builder()
                .name("andres")
                .surname("rpenuela")
                .email("andres.rpenuela@bla.com")
                .password("$2a$12$zNe45Ch0yDeeMoBb0B9X4OSoKuzj5pQAt2LY7VN5F7vq2U9751hna") //pwd: user, https://bcrypt-generator.com/
                .roles(new HashSet<>(roles))
                .build();

        userDao.save(user);
        userDao.flush();
    }

    @AfterEach
    void afterEach(){
        User user = userDao.findByEmail("andres.rpenuela@bla.com").get();

        userDao.delete(user);
    }


    // Cuando se hace un "getRoles()" de "user", lanza una query para recuperarlos, pero como la
    // transacción del "findByEmail" ha finalizado, da un error de LazyInitializationException.class
    @Test
    void givenEmail_whenFindByEmailInCallNoTransactional_thenLaunchLazyInitializationException( ){
        final User user = userDao.findByEmail("andres.rpenuela@bla.com").get();

        Assertions.assertThrows(LazyInitializationException.class,
                ()->user.getRoles().stream().map(Role::getName).toList());
    }

    @Test
    @Transactional // Gestion por Spring de la transacción
    void givenEmail_whenFindByEmailInCallTransactional_thenReturnUserWithDataLazy( ){
        final User user = userDao.findByEmail("andres.rpenuela@bla.com").get();

        assertThat(user).isNotNull()
                .returns("andres",User::getName)
                .satisfies(element -> assertThat(element.getCreated()).isNotNull())
                .satisfies(element -> assertThat(element.getId()).isNotNull())
                .satisfies(element -> assertThat(element.getRoles().stream().map(Role::getName).toList())
                        .isNotNull()
                        .isNotEmpty()
                        .containsExactlyInAnyOrder("USER","ADMIN"));
    }

    @Test
    void givenEmail_whenFindByEmailInCallTransactionalGiven_thenReturnUserWithDataLazy( ){
        // execution inside to Transaction
        this.transactionTemplate.executeWithoutResult(transactionStatus->{
            final User user = userDao.findByEmail("andres.rpenuela@bla.com").get();

            assertThat(user).isNotNull()
                    .returns("andres",User::getName)
                    .satisfies(element -> assertThat(element.getCreated()).isNotNull())
                    .satisfies(element -> assertThat(element.getId()).isNotNull())
                    .satisfies(element -> assertThat(element.getRoles().stream().map(Role::getName).toList())
                            .isNotNull()
                            .isNotEmpty()
                            .containsExactlyInAnyOrder("USER","ADMIN"));
        });
    }
 */
}
