package com.tokioschool.flightapp.flight.store.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokioschool.flightapp.flight.store.StoreFacade;
import com.tokioschool.flightapp.flight.store.dto.ResourceContentDto;
import com.tokioschool.flightapp.flight.store.dto.ResourceIdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service("storeFacadeRestClientImpl")
@RequiredArgsConstructor
@Slf4j
@Primary
public class StoreFacadeRestClientImpl implements StoreFacade {

    private final RestClient restClient;
    private final ObjectMapper objectMapper; // para serializar los objetos

    @Override
    public Optional<ResourceIdDto> saveResource(MultipartFile multipartFile, String description) {

        // simula la creacion de un objeto ResourceDescriptionMap
        HashMap<String,String> descriptionMap = new HashMap<>();
        descriptionMap.put("description", description);
        String descriptionBody;// lo que se va enviar
        try{
            descriptionBody = objectMapper.writeValueAsString(descriptionMap);
        }catch (JsonProcessingException e){
            log.error("Error while parsing file", e);
            descriptionBody = StringUtils.EMPTY;
        }

        // envio del contenido
        MediaType mediaType;
        try{
            mediaType = MediaType.valueOf(multipartFile.getContentType());

        }catch (Exception e){
            log.error("Error while parsing file", e);
            mediaType = MediaType.APPLICATION_OCTET_STREAM; // lo guardia como binario estrictamente
        }

        // se monta el cuerpo del request multipar: objetos diferentes
        MultiValueMap<Object,Object> parts = new LinkedMultiValueMap<Object,Object>();

        // cada parte esta formada por: contendio + cabecera
        HttpHeaders descriptionHeaders = new HttpHeaders();
        descriptionHeaders.setContentType(MediaType.APPLICATION_JSON);
        parts.add("description", new HttpEntity<>(descriptionBody,descriptionHeaders));

        HttpHeaders contentHeaders = new HttpHeaders();
        contentHeaders.setContentType(mediaType);
        parts.add("content",new HttpEntity<>(multipartFile.getResource(),contentHeaders));

        try{
            ResourceIdDto resourceIdDto = restClient.post().uri("/store/api/resources")
                    .contentType(MediaType.MULTIPART_FORM_DATA) //sobrescribe la defindia en el bean
                    .body(parts)
                    .retrieve()
                    .body(ResourceIdDto.class);

            return Optional.ofNullable(resourceIdDto);

        }catch (Exception e){
            log.error("Error saving resource", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ResourceContentDto> findResource(UUID resourceId) {
        final String uri = "/store/api/resources/{resourceId}";
        try{
            ResourceContentDto resourceContentDto = restClient.get()
                    .uri(uri,resourceId)
                    .retrieve()
                    .body(ResourceContentDto.class);

            return Optional.ofNullable(resourceContentDto);
        }catch (Exception e){
            log.error("Exception in findResource",e);
        }

        return Optional.empty();
    }

    @Override
    public void deleteResource(UUID resourceId) {
            restClient.delete().uri("/store/api/resources/{resourceId}",resourceId)
                    .retrieve()
                    .toBodilessEntity();
    }
}
