package com.jungeeks.service.impl;

import com.jungeeks.entity.Family;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.Task;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.response.NotificationResponse;
import com.jungeeks.response.TaskResponse;
import com.jungeeks.service.entity.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = UserServiceImplTest.class)
@Tag("unit")
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    static List<NotificationResponse> notificationResponsesTest;
    static List<TaskResponse> taskResponseTest;
    static User user;


    @BeforeAll
    static void setUp() {
        user  = User.builder()
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

        notificationResponsesTest = List.of(
               NotificationResponse.builder()
                       .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                       .build(),
                NotificationResponse.builder()
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build()
                );

        taskResponseTest = List.of(
                TaskResponse.builder()
                        .title("test1")
                        .point(676L)
                        .taskStatus(TASK_STATUS.ACTIVE)
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build(),
                TaskResponse.builder()
                        .title("test2")
                        .point(633L)
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .taskStatus(TASK_STATUS.ACTIVE)
                        .build()
        );
    }

    @Test
    void getUserById() {
        when(userRepository.findUserById(any()))
                .thenReturn(Optional.ofNullable(user));
        User user1 = userService.getUserById("");
        assertEquals(user1, user);
    }

    @Test
    void canGetNoUserById() {
        when(userRepository.findUserById(any()))
                .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById("1L"));
    }

    @Test
    void getDeadlineOfAllTask() {
        when(userRepository.findUserById(any()))
                .thenReturn(Optional.of(user));
        List<NotificationResponse> notificationResponses = userService.getDeadlineOfAllTask(any());
        assertEquals(notificationResponses,notificationResponsesTest);
    }

    @Test
    void getUserTaskById() {
        when(userRepository.findUserById(any()))
                .thenReturn(Optional.of(user));
        List<TaskResponse> taskResponse = userService.getUserTaskById(any());
        assertEquals(taskResponse,taskResponseTest);
    }
}