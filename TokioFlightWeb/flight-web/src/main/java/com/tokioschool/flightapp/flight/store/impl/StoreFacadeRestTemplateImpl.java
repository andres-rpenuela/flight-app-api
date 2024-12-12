package com.tokioschool.flightapp.flight.store.impl;

import com.tokioschool.flightapp.flight.store.StoreFacade;
import com.tokioschool.flightapp.flight.store.dto.ResourceContentDto;
import com.tokioschool.flightapp.flight.store.dto.ResourceIdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Deprecated
@Service("storeFacadeRestTemplate")
@RequiredArgsConstructor
@Slf4j
public class StoreFacadeRestTemplateImpl implements StoreFacade {

    private final RestTemplate restTemplate;

    @Override
    public Optional<ResourceIdDto> saveResource(MultipartFile multipartFile, String description) {
        return Optional.empty();
    }

    @Override
    public Optional<ResourceContentDto> findResource(UUID resourceId) {
        final String url = "http://loclahost:8081/sotre/api/resources/{resourceId}";
        try{
            ResourceContentDto resourceContentDto = restTemplate.getForObject(url,
                    ResourceContentDto.class,
                    resourceId);

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
