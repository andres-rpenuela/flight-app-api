package com.tokioschool.flightapp.store.scurity.service.impl;

import com.tokioschool.flightapp.store.scurity.jwt.service.JwtService;
import com.tokioschool.flightapp.store.scurity.dto.AuthenticatedMeResponseDTO;
import com.tokioschool.flightapp.store.scurity.dto.AuthenticationRequestDTO;
import com.tokioschool.flightapp.store.scurity.dto.AuthenticationResponseDTO;
import com.tokioschool.flightapp.store.scurity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationResponseDTO) {
        // auntentica el usaurio en el sistema
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationResponseDTO.getUsername(),
                        authenticationResponseDTO.getPassword());

        Authentication authentication = authenticationManager
                .authenticate( usernamePasswordAuthenticationToken  );

        // se obitnete el usuario autenticado
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // generamos el token
        Jwt jwt = jwtService.generateToken(userDetails);


        return AuthenticationResponseDTO.builder()
                .accessToken(jwt.getTokenValue())
                // +1 porque excluye el úlitmo digio del segunod y sea 3600 segundos
                .expiresIn(ChronoUnit.SECONDS.between(Instant.now(), jwt.getExpiresAt()) +1)
                .build();
    }

    @Override
    public AuthenticatedMeResponseDTO getAuthenticated() { // para saber quin hizo la petición
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return AuthenticatedMeResponseDTO.builder()
                .username(authentication.getName())
                .authorities(
                        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                .filter(value -> !value.toUpperCase().startsWith("ROLE_"))
                                .toList()
                )
                .roles(
                        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                .filter(value -> value.toUpperCase().startsWith("ROLE_"))
                                .toList()
                ).build();
    }
}
