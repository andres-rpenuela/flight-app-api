package com.tokioschool.flightapp.store.scurity.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
public class AuthenticationResponseDTO {

    String accessToken;
    long expiresIn;
}
