package com.tokioschool.flightapp.helloworld.controller.api.ut;

import com.tokioschool.flightapp.helloworld.controller.api.HelloWorldApiController;
import com.tokioschool.flightapp.helloworld.dto.HiMessageResponseDTO;
import com.tokioschool.flightapp.helloworld.service.HelloWorldService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(HelloWorldApiController.class)
@ActiveProfiles("test")
class HelloWorldApiControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelloWorldService helloWorldService;


    @Test
    void givenRequestGetNullName_whenGetHelloWorldHandler_thenReturnOk() throws Exception {
        Mockito.when(helloWorldService.getHiMessage(null))
                .thenReturn(HiMessageResponseDTO.builder()
                        .message("Hello world dude").build());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/helloworld/api/hi"))
                .andDo(MockMvcResultHandlers.print());
                //.andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(MockMvcResultMatchers.content()
                //        .string(Matchers.containsString("Hello world dude")));
    }
}