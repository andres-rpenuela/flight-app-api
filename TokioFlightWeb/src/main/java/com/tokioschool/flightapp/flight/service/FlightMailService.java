package com.tokioschool.flightapp.flight.service;

import com.tokioschool.flightapp.email.dto.EmailDTO;
import com.tokioschool.flightapp.flight.dto.FlightDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FlightMailService {
    EmailDTO sendMailFlightSimple(FlightDTO flightDTO,String to);
    EmailDTO sendMailFlightWithAttachment(FlightDTO flightDTO, String to, MultipartFile... multipartFiles);
}
