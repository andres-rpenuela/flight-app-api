package com.tokioschool.flightapp.flight.service.impl;

import com.tokioschool.flightapp.flight.domain.Resource;
import com.tokioschool.flightapp.flight.dto.ResourceDto;
import com.tokioschool.flightapp.flight.repository.ResourceDao;
import com.tokioschool.flightapp.flight.service.ResourceService;
import com.tokioschool.flightapp.flight.store.StoreFacade;
import com.tokioschool.flightapp.flight.store.dto.ResourceContentDto;
import com.tokioschool.flightapp.flight.store.dto.ResourceIdDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final ResourceDao resourceDao;
    @Qualifier("storeFacadeRestClientImpl")
    private final StoreFacade storeFacade;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Optional<ResourceDto> save(MultipartFile multipartFile, String description) {

        return populationCreateOrUpdate(new Resource(), multipartFile, description);
    }

    @Override
    @Transactional
    public Optional<ResourceDto> update(Resource resource, MultipartFile multipartFile, String description) {
        final UUID resourceIdOld = resource.getResourceId();
         Optional<ResourceDto> resourceDtoOpt = populationCreateOrUpdate(resource, multipartFile, description);

        if(resourceDtoOpt.isPresent()) {
            storeFacade.deleteResource(resourceIdOld);
        }
        return resourceDtoOpt;
    }

    protected Optional<ResourceDto> populationCreateOrUpdate(@NonNull Resource resource, MultipartFile multipartFile, String description){
        final Optional<ResourceIdDto> resourceIdDtoOpt = storeFacade.saveResource(multipartFile, description);

        if(resourceIdDtoOpt.isEmpty())
            return Optional.empty();

        ResourceContentDto resourceContentDto = storeFacade.findResource(resourceIdDtoOpt.get().resourceId())
                .orElseThrow(()->new IllegalArgumentException("%s don't find in local, before created!"
                        .formatted(resourceIdDtoOpt.get().resourceId())));

        resource = buildResourceFromResourceContent(resourceContentDto,resourceIdDtoOpt.get().resourceId(),resource.getId());

        return Optional.of(modelMapper.map(resourceDao.save(resource), ResourceDto.class));
    }
    @Override
    public ResourceDto getResourceContent(UUID resourceId) {
        return storeFacade.findResource(resourceId).map(ResourceServiceImpl::mapperResourceContentToResourceDto)
                .orElseThrow(()-> new IllegalArgumentException("%s not found!".formatted(resourceId)));
    }

    @Override
    @Transactional
    public void deleteImage(UUID resourceId) {

        resourceDao.findByResourceId(resourceId)
                  .ifPresent(element -> {
                      resourceDao.delete(element);
                      storeFacade.deleteResource(resourceId);
                  });

    }

    private static ResourceDto mapperResourceContentToResourceDto(ResourceContentDto resourceContentDto) {
        return ResourceDto.builder()
                .resourceId(resourceContentDto.resourceId())
                .filename(resourceContentDto.resourceName())
                .size(resourceContentDto.size())
                .contentType(resourceContentDto.contentType())
                .content(resourceContentDto.content())
                .build();
    }

    private static Resource buildResourceFromResourceContent(@NonNull ResourceContentDto resourceContentDto,@NonNull UUID resourceId, @Nullable Long id) {
        return Resource.builder()
                .id(id)
                .fileName(resourceContentDto.resourceName())
                .resourceId(resourceId)
                .size(resourceContentDto.size())
                .contentType(resourceContentDto.contentType())
                .build();
    }
}
