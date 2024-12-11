package com.tokioschool.flightapp.flight.configuration.mapper.converter;

import com.tokioschool.flightapp.flight.domain.Resource;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Optional;

/**
 * Crea un conversor personalizado de Resoruce a String
 */
public class ResourceToStringConvert implements Converter<Resource,String> {


    @Override
    public String convert(MappingContext<Resource, String> mappingContext) {
        return Optional.ofNullable(mappingContext)
                .map(MappingContext::getSource)
                .map(Resource::getResourceId)
                .map(String::valueOf)
                .orElse(null);
    }

}
