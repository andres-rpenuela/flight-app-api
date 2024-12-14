package com.tokioschool.flightapp.store.controller;

import com.tokioschool.flightapp.store.scurity.dto.AuthenticationRequestDTO;
import com.tokioschool.flightapp.store.scurity.dto.AuthenticationResponseDTO;
import com.tokioschool.flightapp.store.scurity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store/api/auth")
public class AuthenticationApiController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthenticationResponseDTO> postAuthenticated(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequestDTO));
    }
}
