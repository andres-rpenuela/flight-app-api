package com.tokioschool.flightapp.store.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ResourceIdDto(UUID resourceId) {
}
