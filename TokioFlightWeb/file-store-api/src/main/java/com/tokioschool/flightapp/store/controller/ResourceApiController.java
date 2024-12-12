package com.tokioschool.flightapp.store.controller;

import com.tokioschool.flightapp.store.core.exception.NotFoundException;
import com.tokioschool.flightapp.store.dto.ResourceContentDto;
import com.tokioschool.flightapp.store.service.StoreService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

}
