package com.jungeeks.service.impl;

import com.jungeeks.dto.VerifyRequestDto;
import com.jungeeks.service.RequestDtoChecksumService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = EmailServiceImplTest.class)
class EmailServiceImplTest {
    @InjectMocks
    EmailServiceImpl emailService;
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
    private static String link;

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

        link = String.format("%s/registration/email/verify?email=%s&registration_token=%s&checksum=%s",
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
        Mockito.when(checksumService.getChecksum(any(), any())).thenReturn(CHECK_SUM);
        Mockito.doNothing().when(emailSender).send(message);

        emailService.setDomain(domain);
        emailService.setUsername(username);
        emailService.send(verifyRequestDto);

        Mockito.verify(checksumService, Mockito.times(1))
                .getChecksum(verifyRequestDto.getRegistration_token(), verifyRequestDto.getEmail());

        Mockito.verify(emailSender, Mockito.times(1)).send(message);
    }
}