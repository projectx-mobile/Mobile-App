package com.jungeeks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jungeeks.configuration.STORAGE_PROVIDER_ENUM;
import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.dto.enums.SIGN_UP_TYPE;
import com.jungeeks.entitiy.enums.USER_STATUS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(classes = PersonalInfControllerTest.class)
//@WebMvcTest
//@ContextConfiguration(classes = {PersonalInfControllerTest.class, PersonalInfController.class})
@SpringBootTest(/*properties = {"STORAGE_PROVIDER=YANDEX"}*/)
@AutoConfigureMockMvc
public class PersonalInfControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PersonalInfController personalInfController;

    @Test
    public void test() throws Exception {

        UserInfoDto.builder()
                .username("TestUsername")
                .email("TestEmail")
                .signUpType(SIGN_UP_TYPE.APPLE)
                .userStatus(USER_STATUS.ACTIVE)
                .photoPath("")
                .build();

        ObjectWriter ow= new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString();

        this.mockMvc.perform(get("/account/inf"))
                .andDo(print())
                .andExpect(content().json());

    }
}
