package com.jungeeks.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jungeeks.dto.ChildDto;
import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.entity.Family;
import com.jungeeks.entity.User;
import com.jungeeks.filter.SecurityFilter;
import com.jungeeks.service.dto.ParentService;
import com.jungeeks.service.entity.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("integration")
class UserControllerTest {

    @Autowired
    private UserController userController;
    @Autowired
    private SecurityFilter securityFilter;
    private MockMvc mockMvc;
    @MockBean
    private ParentService parentService;
    @MockBean
    private UserService userService;

    @Value("${FIREBASE_PROJECT_ID}")
    private String firebaseProjectId;

    private static ParentHomeDto parentHomeDto;
    private static User user;

    private static final String FAMILY_ID = "familyId";
    private static final String EMAIL = "kidsapptestacc@gmail.com";
    private static final String PASSWORD = "12344321";
    private static final String SIGN_UP_FIREBASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={key}";


    @Autowired
    public void setMockMvc() {
        this.mockMvc = standaloneSetup(userController).addFilters(securityFilter).build();
    }

    @BeforeAll
    static void setUp() {
        parentHomeDto = ParentHomeDto.builder()
                .familyId(FAMILY_ID)
                .childDtos(List.of(
                        ChildDto.builder()
                                .name("child1")
                                .build(),
                        ChildDto.builder()
                                .name("child2")
                                .build(),
                        ChildDto.builder()
                                .name("child3")
                                .build()
                ))
                .build();
        user = User.builder()
                .id(1L)
                .family(Family.builder()
                        .id(FAMILY_ID)
                        .build())
                .build();
    }

    @Test
    void getParentHomePagePositive() throws Exception {
        when(userService.getUserByFirebaseId(any())).thenReturn(user);
        when(parentService.getParentHomeDate(user)).thenReturn(parentHomeDto);

        SignUpResponseDto idTokenFromFirebase1 = getResponseFromFirebase();
        String idTokenFromFirebase = idTokenFromFirebase1.getIdToken();
        String resultParentData = mappingObjectToJson(parentHomeDto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/getParentHome")
                        .header("Authorization", "Bearer " + idTokenFromFirebase))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(resultParentData));
    }


    private String mappingObjectToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(obj);
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