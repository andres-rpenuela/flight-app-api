package com.tokioschool.flightapp.helloworld.controller.mvc.ut;

import com.tokioschool.flightapp.helloworld.controller.mvc.HelloWorldMvcController;
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
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;


@WebMvcTest(HelloWorldMvcController.class)
@ActiveProfiles("test")
class HelloWorldMvcControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelloWorldService helloWorldService;

    @Test
    void givenRequestGetNullName_whenGetHelloWorldHandler_thenReturnOk() throws Exception {
        final String defaultPhrase = "Hello world dude";
        Mockito.when(helloWorldService.getHiMessage(null)).thenReturn(HiMessageResponseDTO.builder().message(defaultPhrase).build());

        final ModelAndView modelAndView = this.mockMvc.perform(MockMvcRequestBuilders.get("/helloworld"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(defaultPhrase)))
                .andReturn().getModelAndView();

        assertThat(modelAndView)
                .extracting(ModelAndView::getModel)
                .isNotNull()
                .satisfiesAnyOf(mapModels-> assertThat(mapModels.values()).contains(defaultPhrase));
    }
}