package com.tokioschool.flightapp.helloworld.service.ut;

import com.tokioschool.flightapp.helloworld.service.HelloWorldService;
import com.tokioschool.flightapp.helloworld.service.HelloWorldServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test Unitario que da cobertura a la implementación de "HelloWordService"
 * sin usar Spring
 */
class HelloWorldServiceImplUnitTest {

    private final HelloWorldService helloWorldService = new HelloWorldServiceImpl();

    // test case over getHiMessage
    @Test
    void givenNullName_whenGetHiMessage_thenReturnHiMessageDefault() {
            final String response = helloWorldService.getHiMessage(null).getMessage();

        Assertions.assertEquals("Hello World dude",response);
    }

    @Test
    void givenEmptyName_whenGetHiMessage_thenReturnHiMessageDefault() {
        final String response = helloWorldService.getHiMessage(StringUtils.EMPTY).getMessage();

        Assertions.assertEquals("Hello World dude",response);
    }

    @Test
    void givenSpaceName_whenGetHiMessage_thenReturnHiMessageDefault() {
        final String response = helloWorldService.getHiMessage(StringUtils.SPACE).getMessage();

        Assertions.assertEquals("Hello World dude",response);
    }

    @Test
    void givenName_whenGetHiMessage_thenReturnHiMessage() {
        final String name = "Andres";
        final String response = helloWorldService.getHiMessage(name).getMessage();

        Assertions.assertEquals("Hello World %s".formatted(name),response);
    }

}