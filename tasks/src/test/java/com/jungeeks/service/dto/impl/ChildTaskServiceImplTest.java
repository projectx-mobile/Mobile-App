package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.ChildNewTaskDto;
import com.jungeeks.entity.Family;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.Task;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.TASK_TYPE;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.entity.FamilyTaskService;
import com.jungeeks.service.entity.TaskService;
import com.jungeeks.service.entity.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ChildTaskServiceImplTest.class)
@Tag("unit")
class ChildTaskServiceImplTest {

    @InjectMocks
    private ChildTaskServiceImpl childTaskService;
    @Mock
    private TaskService taskService;
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private UserService userService;
    @Mock
    private FamilyTaskService familyTaskService;

    private static User user;
    private static SecurityUserFirebase securityUserFirebase;
    private static ChildNewTaskDto childNewTaskDtoWithTemplate;
    private static ChildNewTaskDto childNewTaskDtoWithOutTemplate;
    private static Task task;
    private static FamilyTask familyTask;
    private static Family family;

    private static final String FIREBASE_ID = "testUid";
    private static final String TITLE = "testTitle";

    @BeforeAll
    static void setUp() {
        family = Family.builder()
                .id("familyId")
                .build();
        user = User.builder()
                .id(1L)
                .firebaseId(FIREBASE_ID)
                .family(family)
                .build();
        securityUserFirebase = SecurityUserFirebase.builder()
                .uid(FIREBASE_ID)
                .build();
        childNewTaskDtoWithTemplate = ChildNewTaskDto.builder()
                .template(TITLE)
                .build();
        childNewTaskDtoWithOutTemplate = ChildNewTaskDto.builder()
                .build();
        task = Task.builder()
                .id(1L)
                .description("test")
                .title(TITLE)
                .build();
        familyTask = FamilyTask.builder()
                .id(1L)
                .family(family)
                .author(user)
                .task(task)
                .build();
    }

    @Test
    void saveTaskPositiveWithTemplate() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(user);
        when(taskService.findByTitle(any())).thenReturn(task);
        when(familyTaskService.save(familyTask)).thenReturn(familyTask);

        boolean saveTask = childTaskService.saveTask(childNewTaskDtoWithTemplate);

        assertTrue(saveTask);
    }

    @Test
    void saveTaskPositiveWithOutTemplate() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(user);
        when(taskService.save(any())).thenReturn(task);
        when(familyTaskService.save(familyTask)).thenReturn(familyTask);

        boolean saveTask = childTaskService.saveTask(childNewTaskDtoWithOutTemplate);

        assertTrue(saveTask);
    }

    @Test
    void saveTaskNegativeWithOutTemplate() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(user);
        when(taskService.findByTitle(any())).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> childTaskService.saveTask(childNewTaskDtoWithTemplate));
    }
}