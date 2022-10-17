package com.jungeeks.service.impl;

import com.jungeeks.dto.VerifyRequestDto;
import com.jungeeks.service.EmailService;
import com.jungeeks.service.RequestDtoChecksumService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private RequestDtoChecksumService checksumService;
    @Value("${EMAIL_LINK_VERIFY_DOMAIN}")
    @Setter
    private String domain;
    @Value("${spring.mail.username}")
    @Setter
    private String username;

    @Override
    public boolean send(VerifyRequestDto verifyRequestDto) {
        String checksum = checksumService.getChecksum(verifyRequestDto.getRegistration_token(), verifyRequestDto.getEmail());

        String link = domain + "/registration/email/verify?" +
                "email=" + verifyRequestDto.getEmail() + "&" +
                "registration_token=" + verifyRequestDto.getRegistration_token() + "&" +
                "checksum=" + checksum;

        SimpleMailMessage message = setMessageFields(verifyRequestDto.getEmail(), link);
        emailSender.send(message);
        log.debug("Verify email send from:" + message.getFrom() + " " +
                "to: " + message.getTo() + " " +
                "link: " + link);
        return true;
    }


    private SimpleMailMessage setMessageFields(String email, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(email);
        message.setSubject("email verification");
        message.setText(String.format("""
                Follow the link to confirm your email
                %s
                """, link));
        return message;
    }
}
