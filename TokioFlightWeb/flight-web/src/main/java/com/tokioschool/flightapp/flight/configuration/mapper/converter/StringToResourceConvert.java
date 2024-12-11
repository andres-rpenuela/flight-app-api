package com.tokioschool.flightapp.flight.configuration.mapper.converter;

import com.tokioschool.flightapp.flight.domain.Resource;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.Optional;

public class StringToResourceConvert implements Converter<String,Resource> {


    @Override
    public Resource convert(MappingContext<String,Resource> mappingContext) {
        return Optional.ofNullable(mappingContext)
                .map(MappingContext::getSource)
                .map(stringId -> Resource.builder().id(Long.parseLong(stringId)).build())
                .orElse(null);
    }

}
