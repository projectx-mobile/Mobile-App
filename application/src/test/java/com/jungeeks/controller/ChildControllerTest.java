package com.jungeeks.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jungeeks.entity.Family;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.Task;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.filter.SecurityFilter;
import com.jungeeks.dto.NotificationDto;
import com.jungeeks.dto.TaskDto;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import static java.nio.file.Paths.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("integration")
class ChildControllerTest {
    @Autowired
    private ChildController childController;
    @Autowired
    private SecurityFilter securityFilter;

    @MockBean
    private UserService userService;

    private MockMvc mockMvc;

    @Value("${FIREBASE_PROJECT_ID}")
    private String firebaseProjectId;

    private static final String SIGN_UP_FIREBASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={key}";
    private static final String EMAIL = "kidsapptestacc@gmail.com";
    private static final String PASSWORD = "12344321";

    private static List<NotificationDto> notificationDto;
    private static List<TaskDto> taskDto;
    private static User user;

    @Autowired
    public void setMockMvc() {
        this.mockMvc = standaloneSetup(childController).addFilters(securityFilter).build();
    }

    @BeforeAll
    public static void setUp() {
//init
        user = User.builder()
                .id(1L)
                .email(null)
                .name(null)
                .name(null)
                .user_role(USER_ROLE.ADMIN)
                .family(Family.builder()
                        .id("1")
                        .build())
                .tasks(List.of(
                        FamilyTask.builder()
                                .repeatable(true)
                                .deadline(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                                .rewardPoints(676L)
                                .id(1L)
                                .task(Task.builder().title("test1").build())
                                .taskStatus(TASK_STATUS.ACTIVE)
                                .build(),
                        FamilyTask.builder()
                                .repeatable(false)
                                .deadline(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                                .rewardPoints(633L)
                                .task(Task.builder().title("test2").build())
                                .id(2L)
                                .taskStatus(TASK_STATUS.ACTIVE)
                                .build()))
                .build();

        notificationDto = List.of(
                NotificationDto.builder()
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build(),
                NotificationDto.builder()
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build()
        );

        taskDto = List.of(
                TaskDto.builder()
                        .title("First task")
                        .taskStatus(TASK_STATUS.ACTIVE)
                        .point(1L)
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build(),
                TaskDto.builder()
                        .title("Second task")
                        .taskStatus(TASK_STATUS.ACTIVE)
                        .point(2L)
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build(),
                TaskDto.builder()
                        .title("Third task")
                        .taskStatus(TASK_STATUS.ACTIVE)
                        .point(3L)
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build()
        );

    }

    @Test
    void getDeadlineOfTask() throws Exception {
        when(userService.getDeadlineOfAllTask(any())).thenReturn(notificationDto);
        when(userService.getUserById(any())).thenReturn(user);
        SignUpResponseDto idTokenFromFirebase1 = getResponseFromFirebase();
        String idTokenFromFirebase = idTokenFromFirebase1.getIdToken();
        String resultQuery = mappingObjectToJson(notificationDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/child/deadline")
                        .header("Authorization", "Bearer " + idTokenFromFirebase))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(resultQuery))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    @Test
    void getTasks() throws Exception {
        when(userService.getUserTaskById(any())).thenReturn(taskDto);
        when(userService.getUserById(any())).thenReturn(user);
        SignUpResponseDto idTokenFromFirebase1 = getResponseFromFirebase();
        String idTokenFromFirebase = idTokenFromFirebase1.getIdToken();
        String resultQuery = mappingObjectToJson(taskDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/child/tasks")
                        .header("Authorization", "Bearer " + idTokenFromFirebase))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json(resultQuery))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    private String mappingObjectToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
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