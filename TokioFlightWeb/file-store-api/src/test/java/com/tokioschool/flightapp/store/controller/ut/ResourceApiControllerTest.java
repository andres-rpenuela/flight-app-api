package com.tokioschool.flightapp.store.controller.ut;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokioschool.flightapp.store.controller.ResourceApiController;
import com.tokioschool.flightapp.store.dto.ResourceContentDto;
import com.tokioschool.flightapp.store.scurity.configuration.filter.StoreApiSecurityConfiguration;
import com.tokioschool.flightapp.store.service.StoreService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@WebMvcTest(controllers = ResourceApiController.class) // obtiente solo el contexto del contraldor especificado
@ActiveProfiles("test")
@ContextConfiguration(classes = { // esto moqueará la seguridad
        ResourceApiController.class, // Conrolador a textear
        ResourceApiControllerTest.Init.class, // compnentes
        StoreApiSecurityConfiguration.class, // aplicamos el modelo de seguridad
})
class ResourceApiControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper; // objeto de serizalizcion, includio en el context de WebMvcTest

    @MockitoBean private StoreService storeService;


    @Test
    @WithMockUser(username = "username",authorities = "read-resource")
    void givenExistingResource_whenGet_thenReturnResourceOk() throws Exception {
        // Fases del test:
        // Prepare: prearar aquello que es neceario para la llamada
        // Execution: lo que llama para dar cobertura
        // Assert: Lo que se comprueba

        ResourceContentDto resourceContentDto = ResourceContentDto.builder()
                .resourceId(UUID.randomUUID())
                .description("description")
                .contentType("text/plain")
                .resourceName("hello.txt")
                .size("Hello".length())
                .content("Hello".getBytes(StandardCharsets.UTF_8)).build();

        Mockito.when(storeService.findResource(resourceContentDto.resourceId()))
                .thenReturn(Optional.of(resourceContentDto));

        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/store/api/resources/{resourceId}",resourceContentDto.resourceId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // opcion 1: Lee la respeusta directamente como un objeto
        ResourceContentDto response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ResourceContentDto.class);

        // opcion 2: Lee la respeusta como un mapa de valores, cada atributo del objeto de la respuesta es añadio elemento de un mapa
        Map<String,Object> values = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<Map<String, Object>>() {});

        // codifica el base 64 el contenido, para comprobarlo con el "content" del mapa
        String base64 = Base64.getEncoder().encodeToString(resourceContentDto.content());

        Assertions.assertThat(values).containsEntry("content",base64);
        Assertions.assertThat(response.content()).isEqualTo(resourceContentDto.content());
    }

    @TestConfiguration
    public static class Init {
        // bien que utiliza el filtro de la cena
        @Bean
        public JwtDecoder jwtDecoder() {
            return NimbusJwtDecoder.withSecretKey(new SecretKeySpec("SECRET".getBytes(), "HMAC")).build();
        }
    }
}