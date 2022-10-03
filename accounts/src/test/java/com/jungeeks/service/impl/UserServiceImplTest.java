package com.jungeeks.service.impl;

import com.jungeeks.entitiy.*;
import com.jungeeks.entitiy.enums.TASK_STATUS;
import com.jungeeks.entitiy.enums.USER_ROLE;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.response.NotificationResponse;
import com.jungeeks.response.TaskResponse;
import com.jungeeks.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.criteria.CriteriaBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = UserServiceImplTest.class)
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
                                .daily(true)
                                .deadline(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                                .points(676L)
                                .id(1L)
                                .task(Task.builder().title("test1").build())
                                .taskStatus(TASK_STATUS.ACTIVE)
                                .build(),
                        FamilyTask.builder()
                                .daily(false)
                                .deadline(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                                .points(633L)
                                .task(Task.builder().title("test2").build())
                                .id(2L)
                                .taskStatus(TASK_STATUS.ACTIVE)
                                .build()))
                .confirmationToken(ConfirmationToken.builder()
                        .id(1L)
                        .confirmedAt(LocalDateTime.now())
                        .token("TestToken")
                        .confirmedAt(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .createAt(LocalDateTime.of(2000, Month.MARCH, 2, 3, 4, 5, 6))
                        .expiresAt(LocalDateTime.of(2001, Month.APRIL, 3, 4, 5, 6, 7))
                        .build())

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
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .taskStatus(TASK_STATUS.ACTIVE)
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
        Mockito.when(userRepository.findUserById(any()))
                .thenReturn(Optional.ofNullable(user));

        User user1 = userService.getUserById(1L);
        assertEquals(user1, user);
    }

    @Test
    void canGetNoUserById() {
        Mockito.when(userRepository.findUserById(any()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getDeadlineOfAllTask() {
        //given
        Mockito.when(userRepository.findUserById(any()))
                .thenReturn(Optional.of(user));

        //when
        List<NotificationResponse> notificationResponses = userService.getDeadlineOfAllTask(any());
        //then
        assertEquals(notificationResponses,notificationResponsesTest);

    }

    @Test
    void getUserTaskById() {
        //given
        Mockito.when(userRepository.findUserById(any()))
                .thenReturn(Optional.of(user));
        //when
        List<TaskResponse> taskResponse = userService.getUserTaskById(any());
        //then
        assertEquals(taskResponse,taskResponseTest);
    }
}