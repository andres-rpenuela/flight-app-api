package com.tokioschool.flightapp.store.scurity.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Jacksonized
@Builder
public class AuthenticatedMeResponseDTO {
    String username;
    List<String> authorities;
    List<String> roles;
}
