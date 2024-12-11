package com.tokioschool.flightapp.email.service.impl;

import com.tokioschool.flightapp.email.dto.EmailDTO;
import com.tokioschool.flightapp.email.service.EmailService;
import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;


    @Override
    public void sendEmailBasic(EmailDTO emailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDTO.getTo());
        message.setFrom(emailDTO.getFrom());
        message.setText(emailDTO.getTextBody());
        message.setSubject(emailDTO.getSubject());

        javaMailSender.send(message);
    }

    @Override
    public void sendEmailWithAttachment(EmailDTO emailDTO) {
        final MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(emailDTO.getFrom());
            message.addRecipients(Message.RecipientType.TO, emailDTO.getTo());
            message.setSubject(emailDTO.getSubject(), StandardCharsets.UTF_8.name());

            // body of email (text + attachment)
            final MimeMultipart multipart = new MimeMultipart();
            message.setContent(multipart);

            // text as part of body
            final MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(emailDTO.getTextBody(), StandardCharsets.UTF_8.name());
            multipart.addBodyPart(messageBodyPart);

            // attachment
            emailDTO.getAttachments().forEach(attachmentDTO -> {

                // read content
                ByteArrayDataSource data =  new ByteArrayDataSource(
                        attachmentDTO.content(),attachmentDTO.contentType()
                );
                // insert content as part of body
                try {
                    final MimeBodyPart mimeBodyPart = new MimeBodyPart();
                    mimeBodyPart.setDataHandler(new DataHandler(data));
                    mimeBodyPart.setFileName(attachmentDTO.filename());
                    multipart.addBodyPart(mimeBodyPart);
                } catch (MessagingException e) {
                    log.error("Exception when building adding attachment to mimeMessage," +
                                    " will be ignored, to: {}, subject: {}, cuased: {}",
                            emailDTO.getTo(),
                            emailDTO.getSubject(),
                            e);
                }
            });

        } catch (MessagingException e) {
            log.error("Exception when building mimeMessage," +
                            " to: {}, subject {}, cuased {}",
                    emailDTO.getTo(),
                    emailDTO.getSubject(),
                    e);
            return;
        }

        try {
            javaMailSender.send(message);
        }catch (MailException e) {
            log.error("Exception when send mimeMessage," +
                            " to: {}, subject {}, cuased {}",
                    emailDTO.getTo(),
                    emailDTO.getSubject(),
                    e);
        }
    }
}
