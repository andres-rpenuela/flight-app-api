package com.tokioschool.flightapp.flight.store.impl;

import com.tokioschool.flightapp.flight.store.StoreFacade;
import com.tokioschool.flightapp.flight.store.dto.ResourceContentDto;
import com.tokioschool.flightapp.flight.store.dto.ResourceIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service("storeFacadeRestTemplate")
@RequiredArgsConstructor
public class StoreFacadeImpl implements StoreFacade {
    @Override
    public Optional<ResourceIdDto> saveResource(MultipartFile multipartFile, String description) {
        return Optional.empty();
    }

    @Override
    public Optional<ResourceContentDto> findResource(UUID resourceId) {
        return Optional.empty();
    }

    @Override
    public void deleteResource(UUID resourceId) {

    }
}
