package com.jungeeks.service.impl;

import com.jungeeks.dto.VerifyRequestDto;
import com.jungeeks.service.EmailService;
import com.jungeeks.service.RequestDtoChecksumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private SimpleMailMessage template;
    @Autowired
    private RequestDtoChecksumService checksumService;

    @Value("${EMAIL_LINK_VERIFY_DOMAIN}")
    private String domain;

    @Override
    public void send(VerifyRequestDto verifyRequestDto) {
        String email = verifyRequestDto.getEmail();
        String registration_token = verifyRequestDto.getRegistration_token();
        String checksum = checksumService.getChecksum(registration_token, email);
        String link = domain + "/registration/email/verify?" +
                "email=" + email + "&" +
                "registration_token=" + registration_token + "&" +
                "checksum=" + checksum;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(template.getFrom());
        message.setTo(email);
        message.setSubject(template.getSubject());
        message.setText(String.format(template.getText(), link));
        emailSender.send(message);
        log.debug("Verify email send from:" + message.getFrom() + " " +
                "to: " + message.getTo() + " " +
                "link: " + link);

    }
}
