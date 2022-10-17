package com.jungeeks.controller;

import com.jungeeks.entity.ClientApp;
import com.jungeeks.entity.SecurityUserFirebase;
import com.jungeeks.entity.User;
import com.jungeeks.filter.SecurityFilter;
import com.jungeeks.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@SpringBootTest
@AutoConfigureMockMvc
public class ClientRegistrationControllerTest {

    @Autowired
    ClientRegistrationController clientRegistrationController;
    @Autowired
    private SecurityFilter securityFilter;
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Value("${FIREBASE_PROJECT_ID}")
    private String firebaseProjectId;

//    private static List<ClientApp> list;//TODO: убрал лист

    private static final String FIREBASE_USER_ID = "UDlRPKRG8AaQfqXL3IL3mwXxtl32";
    private static final String NEW_REGISTRATION_TOKEN = "ewf3443wefdd34rssdf";
    private static final String EMAIL = "kidsapptestacc@gmail.com";
    private static final String PASSWORD = "12344321";
    private static final String SIGN_UP_FIREBASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={key}";

    @Autowired
    public void setMockMvc() {
        this.mockMvc = standaloneSetup(clientRegistrationController).addFilters(securityFilter).build();
    }

//    @BeforeAll
//    static void setUp() {
//        list = List.of(
//                ClientApp.builder()
//                        .appId("eferwferc3627348")
//                        .updated(LocalDateTime.of(2020, Month.AUGUST, 12, 10, 30, 10, 32))
//                        .build(),
//                ClientApp.builder()
//                        .appId("fnhdjhdcdfe3fnjs")
//                        .updated(LocalDateTime.of(2020, Month.AUGUST, 12, 10, 30, 10, 32))
//                        .build()
//        );
//    }//TODO: убрал @BeforeAll

    @Test
    @Sql(value = {"/integration/integration-test-users-data-for-security.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/integration/integration-test-users-data-for-security-after-test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void updateRegistrationTokenWithBarerToken() throws Exception {
        SignUpResponseDto idTokenFromFirebase1 = getResponseFromFirebase();
        String idTokenFromFirebase = idTokenFromFirebase1.getIdToken();

        this.mockMvc.perform(post("/registration/client/register")
                        .header("Authorization", "Bearer " + idTokenFromFirebase)
                        .param("registration_token", NEW_REGISTRATION_TOKEN))
                .andDo(print())
                .andExpect(status().isOk());

        User userAfterAddNewToken = userRepository.findByFirebaseId(FIREBASE_USER_ID).orElse(null);
        assertNotNull(userAfterAddNewToken);

        ClientApp clientAppsAfterAddNewToken = userAfterAddNewToken.getClientApps()
                .stream()
                .filter(x -> x.getAppId().equals(NEW_REGISTRATION_TOKEN))
                .findFirst()
                .orElse(null);
        assertNotNull(clientAppsAfterAddNewToken);

        LocalDateTime updated = clientAppsAfterAddNewToken.getUpdated();

        this.mockMvc.perform(post("/registration/client/register")
                        .header("Authorization", "Bearer " + idTokenFromFirebase)
                        .param("registration_token", NEW_REGISTRATION_TOKEN))
                .andDo(print())
                .andExpect(status().isOk());

        User userAfterUpdateToken = userRepository.findByFirebaseId(FIREBASE_USER_ID).orElse(null);
        assertNotNull(userAfterUpdateToken);

        ClientApp clientAppAfterUpdate = userAfterUpdateToken.getClientApps()
                .stream()
                .filter(x -> x.getAppId().equals(NEW_REGISTRATION_TOKEN))
                .findFirst()
                .orElse(null);
        assertNotNull(clientAppAfterUpdate);

        LocalDateTime updatedDate = clientAppAfterUpdate.getUpdated();
        assertTrue(updatedDate.isAfter(updated));
        assertEquals(userAfterAddNewToken.getClientApps().size(), userAfterUpdateToken.getClientApps().size());//TODO: убрал лист
    }


    private SignUpResponseDto getResponseFromFirebase() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("email", EMAIL);
        body.put("password", PASSWORD);
        body.put("returnSecureToken", true);

        Map<String, Object> params = new HashMap<>();
        params.put("key", firebaseProjectId);

        HttpEntity<Map<String, Object>> mapHttpEntity = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(SIGN_UP_FIREBASE_URL, mapHttpEntity, SignUpResponseDto.class, params);
    }
}
