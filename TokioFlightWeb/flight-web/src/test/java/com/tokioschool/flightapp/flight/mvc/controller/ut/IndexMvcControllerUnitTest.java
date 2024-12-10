package com.tokioschool.flightapp.flight.mvc.controller.ut;

import com.tokioschool.flightapp.flight.mvc.controller.IndexMvcController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(IndexMvcController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)// al usar spring security, si no se pone devuelve: MockHttpServletResponse:          Status = 401   Error message = Unauthorized ...
class IndexMvcControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenRequestGetIndex_whenGetIndexPageHandler_thenReturnOk() throws Exception {
        final String content = this.mockMvc.perform(MockMvcRequestBuilders.get("/flight"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        assertThat(content)
                .isNotBlank()
                .hasSizeGreaterThanOrEqualTo(1);
    }

}
