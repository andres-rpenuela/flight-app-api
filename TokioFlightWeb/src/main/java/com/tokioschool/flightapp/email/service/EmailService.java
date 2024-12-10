package com.tokioschool.flightapp.email.service;

import com.tokioschool.flightapp.email.dto.EmailDTO;

public interface EmailService {

    void sendEmailBasic(EmailDTO emailDTO);
    void sendEmailWithAttachment(EmailDTO emailDTO);
}
