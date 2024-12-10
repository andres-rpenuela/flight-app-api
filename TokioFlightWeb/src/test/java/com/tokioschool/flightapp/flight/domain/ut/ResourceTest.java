package com.tokioschool.flightapp.flight.domain.ut;

import com.tokioschool.flightapp.flight.domain.Airport;
import com.tokioschool.flightapp.flight.domain.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ResourceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenResource_whenPersist_thenSuccess() {
        Resource resource = Resource.builder()
                .resourceId(UUID.randomUUID())
                .size(1)
                .fileName("Facebook")
                .contentType("ContentType")
                .build();

        entityManager.persistAndFlush(resource);
        assertThat(resource.getId()).isNotNull();
        assertThat(resource.getFileName()).isEqualTo("Facebook");
    }

}