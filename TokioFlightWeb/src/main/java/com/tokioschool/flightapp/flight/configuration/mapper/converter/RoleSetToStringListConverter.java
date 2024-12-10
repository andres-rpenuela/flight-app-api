package com.tokioschool.flightapp.flight.configuration.mapper.converter;

import com.tokioschool.flightapp.flight.domain.Role;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.List;
import java.util.Set;

public class RoleSetToStringListConverter implements Converter<Set<Role>, List<String>> {


    @Override
    public List<String> convert(MappingContext<Set<Role>, List<String>> context) {
        return context.getSource().stream().map(Role::getName).toList();
    }
}
