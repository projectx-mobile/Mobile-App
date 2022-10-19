package com.jungeeks.service.impl;

import com.jungeeks.dto.VerifyRequestDto;
import com.jungeeks.service.RequestDtoChecksumService;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EmailServiceImplTest.class)
class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;
    @Mock
    private JavaMailSender emailSender;
    @Mock
    private RequestDtoChecksumService checksumService;
    @Value("${EMAIL_LINK_VERIFY_DOMAIN}")
    private String domain;
    @Value("${spring.mail.username}")
    private String username;

    private static VerifyRequestDto verifyRequestDto;
    private static SimpleMailMessage message;

    private static final String DOMAIN = "http://localhost:8095";
    private static final String REQUEST_EMAIL = "kidsapptestacc@gmail.com";
    private static final String REQUEST_REGISTRATION_TOKEN = "12344321";
    private static final String CHECK_SUM = "3702039452";
    private static final String EMAIL_FROM = "KidsAppTestAcc@yandex.by";
    private static final String SUBJECT = "email verification";

    @BeforeAll
    static void setUp() {
        verifyRequestDto = VerifyRequestDto.builder()
                .email(REQUEST_EMAIL)
                .registration_token(REQUEST_REGISTRATION_TOKEN)
                .build();

        String link = String.format("%s/registration/email/verify?email=%s&registration_token=%s&checksum=%s",
                DOMAIN, verifyRequestDto.getEmail(), verifyRequestDto.getRegistration_token(), CHECK_SUM);

        message = new SimpleMailMessage();
        message.setFrom(EMAIL_FROM);
        message.setTo(verifyRequestDto.getEmail());
        message.setSubject(SUBJECT);
        message.setText(String.format("""
                Follow the link to confirm your email
                %s
                """, link));
    }

    @Test
    void send() {
        when(checksumService.getChecksum(any(), any())).thenReturn(CHECK_SUM);
        doNothing().when(emailSender).send(message);

        emailService.setDomain(domain);
        emailService.setUsername(username);
        emailService.send(verifyRequestDto);

        verify(checksumService, times(1))
                .getChecksum(verifyRequestDto.getRegistration_token(), verifyRequestDto.getEmail());

        verify(emailSender, times(1)).send(message);
    }

    @Test
    void sendBadEmail() {
        when(checksumService.getChecksum(any(), any())).thenReturn(CHECK_SUM);

        emailService.setDomain(domain);
        emailService.setUsername(username);
        emailService.send(verifyRequestDto);

        verify(checksumService, times(1))
                .getChecksum(verifyRequestDto.getRegistration_token(), verifyRequestDto.getEmail());

        doThrow(new MailSendException("")).when(emailSender).send(any(SimpleMailMessage.class));

        assertThrows(MailSendException.class, () -> emailService.send(VerifyRequestDto.builder()
                .email("")
                .registration_token(REQUEST_REGISTRATION_TOKEN)
                .build()));
    }
}