package com.tokioschool.flightapp.store.service;

import com.tokioschool.flightapp.store.dto.ResourceContentDto;
import com.tokioschool.flightapp.store.dto.ResourceIdDto;
import jakarta.annotation.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

public interface StoreService {
    Optional<ResourceIdDto> saveResource(MultipartFile multipartFile, @Nullable String description);

    Optional<ResourceContentDto> findResource(UUID resourceId);

    void deleteResource(UUID resourceId);

}
