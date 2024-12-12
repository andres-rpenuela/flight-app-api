package com.tokioschool.flightapp.store.controller;

import com.tokioschool.flightapp.store.core.exception.InternalErrorException;
import com.tokioschool.flightapp.store.core.exception.NotFoundException;
import com.tokioschool.flightapp.store.dto.ResourceContentDto;
import com.tokioschool.flightapp.store.dto.ResourceDescriptionDto;
import com.tokioschool.flightapp.store.dto.ResourceIdDto;
import com.tokioschool.flightapp.store.service.StoreService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/api/resources")
@Validated // de spring
public class ResourceApiController {

    private final StoreService storeService;

    @GetMapping(value = "/{resourceId}",produces = "application/json")
    public ResponseEntity<ResourceContentDto> getResourceHandler(
            @NotNull @PathVariable UUID resourceId) {

        ResourceContentDto resourceContentDto = storeService.findResource(resourceId)
                .orElseThrow(()-> new NotFoundException("Resource with id: %s not found!.".formatted(resourceId)));

        return ResponseEntity.ok(resourceContentDto);
    }


    //@PostMapping(value = "",produces = "application/json",consumes = {"application/octet-stream","application/json"})
    @PostMapping(value = "",produces = "application/json",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResourceIdDto> createResourceHandler(
            @RequestPart("description")ResourceDescriptionDto resourceDescriptionDto,
            @RequestPart("content") MultipartFile multipartFile
            ){
        ResourceIdDto resourceIdDto =  storeService.saveResource(multipartFile,resourceDescriptionDto.getDescription())
                .orElseThrow(() -> new InternalErrorException("There's been an error, try it again later"));

        return ResponseEntity.status(HttpStatus.CREATED).body(resourceIdDto);
    }

    @DeleteMapping(value="/{resourceId}")
    public ResponseEntity<Void> deleteResourceHandler(@Valid @NotNull @PathVariable UUID resourceId) {
        storeService.deleteResource(resourceId);
        return ResponseEntity.noContent().build();
    }
}
