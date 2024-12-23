package com.tokioschool.flightapp.store.scurity.jwt.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.List;

/**
 * Converter de Spring
 */
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        // Convert por defecto de Spring
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        // personalizamos los roles y permisos
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                // se obtiene el campo de "authorities" del payload del token que es un String[]
                // y se convierte a un objeto de tipo "SimpleGrantedAuthoritiy" para adaptarlo
                // a UserDetaisl
                jwt -> ((List<String>) jwt.getClaim("authorities"))
                        .stream().map(authority -> (GrantedAuthority) new SimpleGrantedAuthority(authority))
                        .toList()
        );

        // se convierte el resto de campos por defecto
        return jwtAuthenticationConverter.convert(source);
    }
}
