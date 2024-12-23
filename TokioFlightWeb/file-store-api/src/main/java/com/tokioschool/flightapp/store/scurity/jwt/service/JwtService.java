package com.tokioschool.flightapp.store.scurity.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;

public interface JwtService {
    Jwt generateToken(UserDetails userDetails);
}
