package com.tokioschool.flightapp.helloworld.service;

import com.tokioschool.flightapp.helloworld.dto.HiMessageResponseDTO;

public interface HelloWorldService {

    HiMessageResponseDTO getHiMessage(String name);
}
