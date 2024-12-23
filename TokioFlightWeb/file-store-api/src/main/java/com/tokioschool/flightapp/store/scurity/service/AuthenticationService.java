package com.tokioschool.flightapp.store.scurity.service;

import com.tokioschool.flightapp.store.scurity.dto.AuthenticatedMeResponseDTO;
import com.tokioschool.flightapp.store.scurity.dto.AuthenticationRequestDTO;
import com.tokioschool.flightapp.store.scurity.dto.AuthenticationResponseDTO;

public interface AuthenticationService {
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationResponseDTO);
    AuthenticatedMeResponseDTO getAuthenticated();
}
