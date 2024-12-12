package com.tokioschool.flightapp.flight.store.impl;

import com.tokioschool.flightapp.flight.store.StoreFacade;
import com.tokioschool.flightapp.flight.store.dto.ResourceContentDto;
import com.tokioschool.flightapp.flight.store.dto.ResourceIdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service("storeFacadeRestClientImpl")
@RequiredArgsConstructor
@Slf4j
@Primary
public class StoreFacadeRestClientImpl implements StoreFacade {

    private final RestClient restClient;

    @Override
    public Optional<ResourceIdDto> saveResource(MultipartFile multipartFile, String description) {
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

    }
}
