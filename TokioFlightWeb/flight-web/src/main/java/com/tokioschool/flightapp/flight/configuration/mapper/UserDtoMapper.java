package com.tokioschool.flightapp.flight.configuration.mapper;


import com.tokioschool.flightapp.flight.configuration.mapper.converter.RoleSetToStringListConverter;
import com.tokioschool.flightapp.flight.domain.User;
import com.tokioschool.flightapp.flight.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserDtoMapper {
    // sobrecarga del mode mapper
    private final ModelMapper modelMapper;

    public UserDtoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureUserDTOConverter();
    }

    private void configureUserDTOConverter(){
        modelMapper.typeMap(User.class, UserDTO.class)
                .addMappings(mapping ->
                    mapping.using(new RoleSetToStringListConverter()).map(User::getRoles,UserDTO::setRoles)
                );
    }
}
