package com.tokioschool.flightapp.flight.service;

import com.tokioschool.flightapp.flight.dto.UserDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public interface UserService {

    Optional<Pair<UserDTO,String>> findUserAndPasswordByEmail(String mail);
    Optional<UserDTO> findByEmail(String email);
}
