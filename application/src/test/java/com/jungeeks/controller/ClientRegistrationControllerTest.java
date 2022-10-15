package com.jungeeks.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.jungeeks.entity.ClientApp;
import com.jungeeks.entity.SecurityUserFirebase;
import com.jungeeks.entity.User;
import com.jungeeks.filter.SecurityFilter;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.SecurityService;
import com.jungeeks.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ClientRegistrationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    ClientRegistrationController clientRegistrationController;
    @Autowired
    private SecurityFilter securityFilter;

    @Mock
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private SecurityService securityService;

    @Value("${FIREBASE_PROJECT_ID}")
    private String firebaseProjectId;

    private static SecurityUserFirebase securityUserFirebaseWithSameEmail;
    private static List<ClientApp> clientApps;
    private static final String FIREBASE_USER_ID = "UDlRPKRG8AaQfqXL3IL3mwXxtl32";

    private static final String REGISTRATION_TOKEN = "12344321";
    private static final String EMAIL = "kidsapptestacc@gmail.com";
    private static final String PASSWORD = "12344321";
    private static final String SIGN_UP_FIREBASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={key}";

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Autowired
    public void setMockMvc() {
        this.mockMvc = standaloneSetup(clientRegistrationController).addFilters(securityFilter).build();
    }

    @BeforeAll
    static void setUp() {
        securityUserFirebaseWithSameEmail = SecurityUserFirebase.builder()
                .uid(FIREBASE_USER_ID)
                .email("test@gmail.com")
                .build();
        clientApps = List.of(
                        ClientApp.builder()
                            .id(1L)
                            .appId("app_id1")
                            .updated(LocalDateTime.of(2022, 10, 15, 0,0,0))
                            .build(),
                        ClientApp.builder()
                                .id(1L)
                                .appId("app_id2")
                                .updated(LocalDateTime.of(2022, 10, 14, 0,0,0))
                                .build(),
                        ClientApp.builder()
                                .id(1L)
                                .appId("app_id3")
                                .updated(LocalDateTime.of(2022, 10, 13, 0,0,0))
                                .build(),
                        ClientApp.builder()
                                .id(1L)
                                .appId("app_id4")
                                .updated(LocalDateTime.of(2022, 10, 12, 0,0,0))
                                .build());
    }


    @Test
    @Sql(value = {"/integration/integration-test-users-data-for-security.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void registerClientWithBarerToken() throws Exception {
        String idTokenFromFirebase = getIdTokenFromFirebase();

        this.mockMvc.perform(post("/registration/client/register")
                        .header("Authorization", "Bearer " + idTokenFromFirebase)
                        .param("registration_token", REGISTRATION_TOKEN))
                .andDo(print())
                .andExpect(status().isOk());

        User user = userRepository.findByFirebaseId(FIREBASE_USER_ID).orElse(null);
        List<ClientApp> list = Objects.requireNonNull(user).getClientApps();
        assertEquals(list.get(4).getAppId(), REGISTRATION_TOKEN);
    }




    private String getIdTokenFromFirebase() {
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

        SignUpResponseDto signUpResponseDto = restTemplate.postForObject(SIGN_UP_FIREBASE_URL,
                mapHttpEntity, SignUpResponseDto.class, params);
        return signUpResponseDto.getIdToken();
    }
}
