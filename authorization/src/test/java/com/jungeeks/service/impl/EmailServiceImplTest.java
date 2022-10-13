package com.jungeeks.service.impl;

import com.jungeeks.dto.VerifyRequestDto;
import com.jungeeks.service.RequestDtoChecksumService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = EmailServiceImplTest.class)
class EmailServiceImplTest {

    @InjectMocks
    EmailServiceImpl emailService;
    @Mock
    private JavaMailSender emailSender;

    @Mock
    private RequestDtoChecksumService checksumService;

    private static VerifyRequestDto verifyRequestDto;
    private static SimpleMailMessage message;
    private static final String domain = "http://localhost:8095";
    private static final String checkSum = "2222";
    private static String link;

    @BeforeAll
    static void setUp() {
        verifyRequestDto = VerifyRequestDto.builder()
                .email("test@gmail.com")
                .registration_token("testToken")
                .build();
        link = domain + "/registration/email/verify?" +
                "email=" + verifyRequestDto.getEmail() + "&" +
                "registration_token=" + verifyRequestDto.getRegistration_token() + "&" +
                "checksum=" + checkSum;;
    }

    @Test
    void send() {
//        message = new SimpleMailMessage();
//        message.setFrom(template.getFrom());
//        message.setTo(verifyRequestDto.getEmail());
//        message.setSubject(template.getSubject());
//        message.setText(String.format(template.getText(), link));
//
//
//        Mockito.when(checksumService.getChecksum(any(), any())).thenReturn(checkSum);
//        Mockito.doNothing().when(emailSender).send(message);
//
//        emailService.send(verifyRequestDto);
//
//        Mockito.verify(checksumService, Mockito.times(1)).getChecksum(verifyRequestDto.getEmail(),
//                verifyRequestDto.getRegistration_token());
//        Mockito.verify(emailSender, Mockito.times(1)).send(message);
    }
}