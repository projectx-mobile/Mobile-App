package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.NotificationDto;
import com.jungeeks.dto.TaskDto;
import com.jungeeks.entity.Family;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.Task;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.entity.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ChildServiceImplTest.class)
@Tag("unit")
class ChildServiceImplTest {

    @InjectMocks
    private ChildServiceImpl childService;

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private UserServiceImpl userService;

    private static User user;

    private static List<NotificationDto> notificationDtoList;

    private static List<TaskDto> taskDto;

    private static SecurityUserFirebase securityUserFirebase;

    private static final String UID = "123";

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

        notificationDtoList = List.of(
                NotificationDto.builder()
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build(),
                NotificationDto.builder()
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build()
        );

        taskDto = List.of(
                TaskDto.builder()
                        .title("test1")
                        .point(676L)
                        .taskStatus(TASK_STATUS.ACTIVE)
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build(),
                TaskDto.builder()
                        .title("test2")
                        .point(633L)
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .taskStatus(TASK_STATUS.ACTIVE)
                        .build()
        );
        securityUserFirebase = SecurityUserFirebase.builder()
                .uid(UID)
                .build();

    }

    @Test
    void getDeadlineOfAllTask() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(any())).thenReturn(user);

        List<NotificationDto> notificationDto = childService.getDeadlineOfAllTask();
        assertEquals(notificationDto, notificationDtoList);
    }

    @Test
    void getUserTaskById() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(any())).thenReturn(user);

        List<TaskDto> tasksDto = childService.getUserTaskById();
        assertEquals(taskDto, tasksDto);

    }

}