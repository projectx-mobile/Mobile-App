package com.jungeeks.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jungeeks.controller.PersonalInfController;
import com.jungeeks.dto.FamilyMemberDto;
import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.repository.AccountsUserRepository;
import com.jungeeks.controller.SignUpResponseDto;
import com.jungeeks.filter.SecurityFilter;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.SecurityService;
import com.jungeeks.service.UserService;
import com.jungeeks.service.busines.RegisterUserService;
import com.jungeeks.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("integration")
public class PersonalInfControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private PersonalInfController personalInfController;

    @Autowired
    private SecurityFilter securityFilter;

    @Value("${FIREBASE_PROJECT_ID}")
    private String firebaseProjectId;

//    private static final String EMAIL = "kidsapptestacc@gmail.com";
    private static final String EMAIL = "parent.test1@gmail.com";
    private static final String PASSWORD = "12344321";
    private static final String SIGN_UP_FIREBASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={key}";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    public void setMockMvc() {
        this.mockMvc = standaloneSetup(personalInfController).addFilters(securityFilter).build();
    }

    @Test
    @Sql(value = {"/integration/personal-inf-controller/integration-test-users-data-for-personal-inf-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/integration/personal-inf-controller/integration-test-users-data-for-personal-inf-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getPersonalInfo() throws Exception {
        SignUpResponseDto responseFromFirebase = getResponseFromFirebase();
        String idTokenFromFirebase = responseFromFirebase.getIdToken();

        UserInfoDto responseDto = UserInfoDto.builder()
                .username("parentTest1")
                .user_role(USER_ROLE.PARENT)
                .photoPath("test1.jpeg")
                .familyMembers(
                        List.of(FamilyMemberDto.builder()
                                        .id(2L)
                                        .username("childTest1")
                                        .photoPath("test2.jpeg")
                                        .build(),
                                FamilyMemberDto.builder()
                                        .id(3L)
                                        .username("childTest2")
                                        .photoPath("test3.jpeg")
                                        .build()))
                .build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String responseDtoJson = ow.writeValueAsString(responseDto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/account/inf")
                                .header("Authorization", "Bearer " + idTokenFromFirebase)
                        /*.param("registration_token", NEW_REGISTRATION_TOKEN)*/)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseDtoJson));
        System.out.println("");
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
