package com.jungeeks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jungeeks.dto.VerifyRequestDto;
import com.jungeeks.dto.controller.EmailRegistrationController;
import com.jungeeks.filter.SecurityFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
public class EmailRegistrationControllerTest {
    private MockMvc mockMvc;
    @Autowired
    EmailRegistrationController emailRegistrationController;
    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    public void setMockMvc() {
        this.mockMvc = standaloneSetup(emailRegistrationController).addFilters(securityFilter).build();
    }

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);


    @Test
    public void verify() throws Exception {
        VerifyRequestDto verifyRequestDto = VerifyRequestDto.builder()
                .email("kidsapptestacc@gmail.com")
                .registration_token("PGwLFCXcP4b6HjGOXQkaxv3tJwn2")
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(verifyRequestDto);

        this.mockMvc.perform(post("/registration/email/verify")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void vefiryLink() throws Exception {
//        MockHttpServletRequestBuilder multipart = multipart(get("/registration/email/verify"))
//                .param("registration_token", "PGwLFCXcP4b6HjGOXQkaxv3tJwn2")
//                .param("checksum", "3493")
//                .param("email", "kidsapptestacc@gmail.com");
//        this.mockMvc.perform(multipart)
//                .andDo(print())
//                .andExpect(model().attribute("email", "kidsapptestacc@gmail.com"))
//                .andExpect(model().attribute("validate", "true"))
//                .andExpect(view().name("emailVerify"));
    }
}

