package com.tokioschool.flightapp.helloworld.service;

import com.tokioschool.flightapp.helloworld.dto.HiMessageResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HelloWorldServiceImpl implements HelloWorldService {

    private static final String DEFAULT_HI_PRHASE = "Hello World dude";
    private static final String HI_PRHASE = "Hello World %s";

    @Override
    public HiMessageResponseDTO getHiMessage(String name) {
        final String message = Optional.ofNullable(name)
                .map(StringUtils::stripToNull)
                .map(HI_PRHASE::formatted)
                .orElse(DEFAULT_HI_PRHASE);

        return HiMessageResponseDTO.builder().message(message).build();
    }
}
