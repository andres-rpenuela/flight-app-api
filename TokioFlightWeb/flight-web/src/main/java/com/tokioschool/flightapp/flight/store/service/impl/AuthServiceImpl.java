package com.tokioschool.flightapp.flight.store.service.impl;

import com.tokioschool.flightapp.flight.store.config.properties.StoreConfigurationProperties;
import com.tokioschool.flightapp.flight.store.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final StoreConfigurationProperties storeConfigurationProperties;

    // RestCliente personalizado, con cabeeras especificas
    private final RestClient restClient = RestClient.builder().build();

    private String accessToken;
    private long expiresIn;

    @Override
    public String getAccessToken() {
        // comprueba si es valdio el token aun
        if( System.currentTimeMillis() < expiresIn ) {
            return accessToken;
        }

        // realiza la peticion
        Map<String,String> authRequest = Map.of("username",storeConfigurationProperties.user().username(),
                "password",storeConfigurationProperties.user().password());

        try{
            AuthResponseDTO authResponseDTO = restClient.post()
                    .uri(storeConfigurationProperties.baseUrl()+"/store/api/auth")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(authRequest)
                    .retrieve()
                    .body(AuthResponseDTO.class);

            accessToken = authResponseDTO.accessToken();
            expiresIn = authResponseDTO.expiresIn();

        }catch (Exception e) {
            log.error("Exception in file-sotre auth endpoint", e);
        }

        // delvelra el token bueno, el viejo o nulo
        return accessToken;
    }

    private record AuthResponseDTO(String accessToken, int expiresIn) {
        public long getExpiresAt() {
            // expiresIn se pasa a segundos y se resta 5 segudnos para que no pille en medio de una peticion
            return System.currentTimeMillis() + expiresIn * 1000L - 5*1000L;
        }
    }
}
