package com.jungeeks.service.dto.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.jungeeks.dto.ChildNewTaskDto;
import com.jungeeks.entity.*;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.business.FirebaseService;
import com.jungeeks.service.entity.FamilyTaskService;
import com.jungeeks.service.entity.TaskService;
import com.jungeeks.service.entity.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ChildTaskServiceImplTest.class)
@Tag("unit")
class ChildTaskServiceImplTest {

    @InjectMocks
    private ChildTaskServiceImpl childTaskService;
    @Mock
    private TaskService taskService;
    @Mock
    private FirebaseService firebaseService;
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private UserService userService;
    @Mock
    private FamilyTaskService familyTaskService;

    private static User child;
    private static SecurityUserFirebase securityUserFirebase;
    private static ChildNewTaskDto childNewTaskDtoWithTemplate;
    private static ChildNewTaskDto childNewTaskDtoWithOutTemplate;
    private static Task task;
    private static FamilyTask familyTask;
    private static Family family;
    private static User parent;
    private static List<User> parents;

    private static final String FIREBASE_ID = "testUid";
    private static final String TITLE = "testTitle";

    @BeforeAll
    static void setUp() {
        family = Family.builder()
                .id("familyId")
                .build();
        child = User.builder()
                .id(1L)
                .name("Child")
                .firebaseId(FIREBASE_ID)
                .family(family)
                .build();
        parent = User.builder()
                .id(2L)
                .name("Parent")
                .email("parent.test1@gmail.com")
                .clientApps(List.of(ClientApp.builder()
                        .id(1L)
                        .appId("appId1")
                        .build()))
                .parentNotifications(ParentNotification.builder()
                        .allOff(false)
                        .newRequest(true)
                        .build())
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
                .author(child)
                .task(task)
                .build();
        parents = List.of(parent);
    }

    @Test
    void saveTaskPositiveWithTemplate() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(child);
        when(taskService.findByTitle(any())).thenReturn(task);
        when(familyTaskService.save(familyTask)).thenReturn(familyTask);
        when(userService.getAllByFamilyIdAndUserRoleWithAdmin(any(), any())).thenReturn(parents);
        when(firebaseService.sendMessageForAll(any(),any(), any())).thenReturn(true);

        boolean saveTask = childTaskService.saveTask(childNewTaskDtoWithTemplate);

        assertTrue(saveTask);
    }

    @Test
    void saveTaskPositiveWithOutTemplate() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(child);
        when(taskService.save(any())).thenReturn(task);
        when(familyTaskService.save(familyTask)).thenReturn(familyTask);
        when(firebaseService.sendMessageForAll(any(),any(), any())).thenReturn(true);

        boolean saveTask = childTaskService.saveTask(childNewTaskDtoWithOutTemplate);

        assertTrue(saveTask);
    }

    @Test
    void saveTaskNegativeWithOutTemplate() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(child);
        when(taskService.findByTitle(any())).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> childTaskService.saveTask(childNewTaskDtoWithTemplate));
    }

    @Test
    void saveTaskNegativeWithOutParents() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(child);
        when(taskService.findByTitle(any())).thenReturn(task);
        when(familyTaskService.save(familyTask)).thenReturn(familyTask);
        when(userService.getAllByFamilyIdAndUserRoleWithAdmin(any(), any())).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> childTaskService.saveTask(childNewTaskDtoWithTemplate));
    }
}