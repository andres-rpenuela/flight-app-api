package com.tokioschool.flightapp.store.controller;

import com.tokioschool.flightapp.store.scurity.dto.AuthenticatedMeResponseDTO;
import com.tokioschool.flightapp.store.scurity.dto.AuthenticationRequestDTO;
import com.tokioschool.flightapp.store.scurity.dto.AuthenticationResponseDTO;
import com.tokioschool.flightapp.store.scurity.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/api/auth")
public class AuthenticationApiController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponseDTO> postAuthenticated(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDTO));
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "auth-openapi")
    public ResponseEntity<AuthenticatedMeResponseDTO> getAuthenticatedMe() {
        return ResponseEntity.ok(authenticationService.getAuthenticated());
    }
}
