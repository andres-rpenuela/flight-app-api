package com.tokioschool.flightapp.flight.service;

import com.tokioschool.flightapp.flight.domain.Resource;
import com.tokioschool.flightapp.flight.dto.ResourceDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface ResourceService {

    Optional<ResourceDto> save(MultipartFile multipartFile, String description);
    Optional<ResourceDto> update(Resource resource, MultipartFile multipartFile, String description);

    ResourceDto getResourceContent(UUID resourceId);

    void deleteImage(UUID resourceId);
}
