package com.jungeeks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.jungeeks.dto.VerifyRequestDto;
import com.jungeeks.filter.SecurityFilter;
import com.jungeeks.service.EmailService;
import com.jungeeks.service.impl.RequestDtoChecksumServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailRegistrationControllerTest {

    @Autowired
    private EmailRegistrationController emailRegistrationController;
    @Autowired
    private SecurityFilter securityFilter;
    @Mock
    private EmailService emailService;
    @Mock
    private RequestDtoChecksumServiceImpl requestDtoChecksumService;
    @Mock
    private FirebaseMessaging firebaseMessaging;
    @Mock
    private JavaMailSender emailSender;
    private MockMvc mockMvc;

    private static Message messageForFirebase;
    private static VerifyRequestDto verifyRequestDto;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    private static final String REQUEST_EMAIL = "kidsapptestacc@gmail.com";
    private static final String REQUEST_REGISTRATION_TOKEN = "12344321";
    private static final String CHECK_SUM = "3702039452";
    private static final String WRONG_CHECK_SUM = "0";
    private static final String URL_VERIFY = "/registration/email/verify";

    @Autowired
    public void setMockMvc() {
        this.mockMvc = standaloneSetup(emailRegistrationController).addFilters(securityFilter).build();
    }

    @BeforeAll
    static void setUp() {
        verifyRequestDto = VerifyRequestDto.builder()
                .email(REQUEST_EMAIL)
                .registration_token(REQUEST_REGISTRATION_TOKEN)
                .build();

        messageForFirebase = Message.builder()
                .putData("email", REQUEST_EMAIL)
                .putData("verify", "true")
                .setToken(REQUEST_REGISTRATION_TOKEN)
                .build();
    }

    @Test
    public void verify() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(verifyRequestDto);

        when(requestDtoChecksumService.getChecksum(any(), any())).thenReturn(CHECK_SUM);
        when(emailService.send(verifyRequestDto)).thenReturn(true);
        doNothing().when(emailSender).send((SimpleMailMessage) any());

        this.mockMvc.perform(post(URL_VERIFY)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void verifyLinkWithCorrectChecksum() throws Exception {
        when(firebaseMessaging.send(messageForFirebase)).thenReturn("false");

        this.mockMvc.perform(get(URL_VERIFY)
                    .param("registration_token", REQUEST_REGISTRATION_TOKEN)
                    .param("checksum", CHECK_SUM)
                    .param("email", REQUEST_EMAIL))
                .andDo(print())
                .andExpect(model().attribute("email", REQUEST_EMAIL))
                .andExpect(model().attribute("validate", true))
                .andExpect(view().name("emailVerify"));
    }

    @Test
    public void verifyLinkWithInCorrectChecksum() throws Exception {
        this.mockMvc.perform(get(URL_VERIFY)
                        .param("registration_token", REQUEST_REGISTRATION_TOKEN)
                        .param("checksum", WRONG_CHECK_SUM)
                        .param("email", REQUEST_EMAIL))
                .andDo(print())
                .andExpect(model().attribute("email", REQUEST_EMAIL))
                .andExpect(model().attribute("validate", false))
                .andExpect(view().name("emailVerify"));
    }
}

