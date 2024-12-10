package com.tokioschool.flightapp.config.translation.ut;

import com.tokioschool.flightapp.flight.configuration.internazionalitation.MessageSourceConfiguration;
import com.transferwise.icu.ICUMessageSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ICUMessageSourceTest {

    private ICUMessageSource messageSource;


    @BeforeEach
    public void setUp() {
        this.messageSource = new MessageSourceConfiguration()
                .icuMessageSource();

    }

    @Test
    void givenKeyMessagePlural_whenTranslate_thenMessagePlural(){
        Map<String,Object> args = new HashMap<>();
        args.put("0",2);

        final String messageEs = messageSource.getMessage("flights.result.list",args, new Locale("es"));

        assertThat(messageEs).isEqualTo("2 vuelos");
    }
}
