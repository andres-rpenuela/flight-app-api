package com.tokioschool.flightapp.flight.service.impl;

import com.tokioschool.flightapp.flight.dto.AirportDTO;
import com.tokioschool.flightapp.flight.repository.AirportDao;
import com.tokioschool.flightapp.flight.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportServiceImp implements AirportService {

    private final AirportDao airportDao;
    private final ModelMapper modelMapper;

    @Override
    public List<AirportDTO> getAllAirports() {
        return airportDao.findAll().stream()
                .map(airport -> modelMapper.map(airport, AirportDTO.class))
                .toList();
    }
}
