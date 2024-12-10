package com.tokioschool.flightapp.flight.service.impl.it;

import com.tokioschool.flightapp.email.dto.AttachmentDTO;
import com.tokioschool.flightapp.email.dto.EmailDTO;
import com.tokioschool.flightapp.email.service.EmailService;
import com.tokioschool.flightapp.flight.dto.FlightDTO;
import com.tokioschool.flightapp.flight.dto.STATUS_FLIGHT_DTO;
import com.tokioschool.flightapp.flight.service.FlightMailService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("mail")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightMailServiceITest {

    @Autowired
    private FlightMailService flightMailService;

    @SpyBean
    private EmailService emailService;


    /**
     * Test de integración que prueba el envio de email y compreuba
     * que se genra bien el EMAIL DTO, que se realiza con un metodo privado en el servicio
     */
    @Test
    @Order(1)
    void givenFlightDTOWithAttachment_whenSendMailFlightSimple_thenBuildEmailDTOSuccess() {
        final FlightDTO flightDTOMock = generatedFlightDTOWithOutAttachmentMock();
        final String to = "andresruizpenuela@gmail.com";

        // send email
        flightMailService.sendMailFlightSimple(flightDTOMock,to);

        // after send, we capture the argument to
        ArgumentCaptor<EmailDTO> emailDTOArgumentCaptor = ArgumentCaptor.forClass(EmailDTO.class);
        Mockito.verify(emailService,Mockito.times(1)).sendEmailBasic(emailDTOArgumentCaptor.capture());

        assertThat(emailDTOArgumentCaptor.getValue().getTo()).isEqualTo(to);
    }

    @Test
    void givenFlightDTOWithAttachment_whenSendMailFlightWithAttachment_thenBuildEmailDTOSuccess() throws IOException {
        final FlightDTO flightDTOMock = generatedFlightDTOWithAttachmentMock();
        final String to = "andresruizpenuela@gmail.com";

        // send email
        flightMailService.sendMailFlightWithAttachment(flightDTOMock,to,generatedMultipartMock());

        // after send, we capture the argument to
        ArgumentCaptor<EmailDTO> emailDTOArgumentCaptor = ArgumentCaptor.forClass(EmailDTO.class);
        Mockito.verify(emailService,Mockito.times(1)).sendEmailWithAttachment(emailDTOArgumentCaptor.capture());

        assertThat(emailDTOArgumentCaptor.getValue())
                .returns(to, EmailDTO::getTo)
                .returns(generatedMultipartMock().getName(), emailDTO -> emailDTO.getAttachments().getFirst().filename());
    }

    private FlightDTO generatedFlightDTOWithOutAttachmentMock() {
        return FlightDTO.builder()
                .number("001")
                .capacity(1)
                .occupancy(1)
                .airportArrivalAcronym("BCN")
                .airportDepartureAcronym("GLA")
                .status(STATUS_FLIGHT_DTO.SCHEDULED)
                .departureTime(LocalDateTime.of(2024,05,01,0,0,0))
                .build();
    }
    private FlightDTO generatedFlightDTOWithAttachmentMock() throws IOException {
        return FlightDTO.builder()
                .number("001")
                .capacity(1)
                .occupancy(1)
                .airportArrivalAcronym("BCN")
                .airportDepartureAcronym("GLA")
                .status(STATUS_FLIGHT_DTO.SCHEDULED)
                .departureTime(LocalDateTime.of(2024,05,01,0,0,0))
                .flightImg(generatedMultipartMock().getOriginalFilename())
                .build();
    }

    private MockMultipartFile generatedMultipartMock() throws IOException {

        final File attachmentFile = new File(getClass()
                .getClassLoader()
                .getResource("static/images/flight-default.jpg")
                .getFile());


        final byte[] contentAttachment = Files.readAllBytes(attachmentFile.toPath());

        return new MockMultipartFile("flight-default","flight-default.jpg","image/jpeg",contentAttachment);
    }
}
