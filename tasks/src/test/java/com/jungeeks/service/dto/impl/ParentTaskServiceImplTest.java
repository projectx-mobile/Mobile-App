package com.jungeeks.service.dto.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.jungeeks.dto.ConfirmTaskDto;
import com.jungeeks.dto.ParentNewTaskDto;
import com.jungeeks.entity.*;
import com.jungeeks.entity.enums.TASK_STATUS;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ParentTaskServiceImplTest.class)
@Tag("unit")
class ParentTaskServiceImplTest {

    @InjectMocks
    private ParentTaskServiceImpl parentTaskService;

    @Mock
    private FirebaseService firebaseService;
    @Mock
    private TaskService taskService;
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private UserService userService;
    @Mock
    private FamilyTaskService familyTaskService;

    private static User parent;
    private static User child;
    private static List<User> childs;
    private static User userWithWrongFamilyId;
    private static FamilyTask familyTask;
    private static FamilyTask familyTaskWithActiveStatus;
    private static ParentNewTaskDto parentNewTaskDtoWithTemplate;
    private static ParentNewTaskDto parentNewTaskDtoWithOutTemplate;
    private static Family family;
    private static SecurityUserFirebase securityUserFirebase;
    private static Task task;
    private static ConfirmTaskDto confirmTaskDto;

    private static final String FAMILY_ID = "familyId";
    private static final String FIREBASE_ID = "firebaseId";
    private static final String TITLE = "title";

    @BeforeAll
    static void setUp() {
        userWithWrongFamilyId = User.builder()
                .family(Family.builder()
                        .id("test")
                        .build())
                .id(2L)
                .build();
        securityUserFirebase = SecurityUserFirebase.builder()
                .uid(FIREBASE_ID)
                .build();
        family = Family.builder()
                .id(FAMILY_ID)
                .build();
        parent = User.builder()
                .id(1L)
                .email("parent.email@gmail.com")
                .name("parent")
                .firebaseId(FIREBASE_ID)
                .family(family)
                .build();
        child = User.builder()
                .id(2L)
                .email("child.email@gmail.com")
                .name("child")
                .family(family)
                .childNotifications(ChildNotification.builder()
                        .allOff(false)
                        .confirmTask(true)
                        .build())
                .clientApps(List.of(ClientApp.builder()
                        .appId("test")
                        .build()))
                .build();
        childs = List.of(child);
        task = Task.builder()
                .id(1L)
                .title(TITLE)
                .build();
        familyTask = FamilyTask.builder()
                .id(1L)
                .author(parent)
                .task(task)
                .family(family)
                .users(childs)
                .author(child)
                .taskStatus(TASK_STATUS.PENDING)
                .build();
        familyTaskWithActiveStatus = FamilyTask.builder()
                .family(family)
                .taskStatus(TASK_STATUS.ACTIVE)
                .build();
        parentNewTaskDtoWithTemplate = ParentNewTaskDto.builder()
                .template(TITLE)
                .userIds(List.of(2L))
                .build();
        parentNewTaskDtoWithOutTemplate = ParentNewTaskDto.builder()
                .userIds(List.of(2L))
                .build();
        confirmTaskDto = ConfirmTaskDto.builder()
                .taskId(1L)
                .build();
    }

    @Test
    void saveTaskPositiveWithTemplate() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(parent);
        when(userService.getUserByUid(any())).thenReturn(child);
        when(taskService.findByTitle(any())).thenReturn(task);
        when(userService.getAllByFamilyIdAndUserRole(any(), any())).thenReturn(childs);
        when(firebaseService.sendMessage(any(),any(), any(), any())).thenReturn(true);

        boolean save = parentTaskService.saveTask(parentNewTaskDtoWithTemplate);

        assertTrue(save);
    }

    @Test
    void saveTaskPositiveWithOutTemplate() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(parent);
        when(userService.getUserByUid(any())).thenReturn(child);
        when(taskService.save(any())).thenReturn(task);
        when(firebaseService.sendMessage(any(),any(), any(), any())).thenReturn(true);

        boolean save = parentTaskService.saveTask(parentNewTaskDtoWithOutTemplate);

        assertTrue(save);
    }

    @Test
    void saveTaskNegativeWithBadUserIds() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(parent);
        when(userService.getUserByUid(any())).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> parentTaskService.saveTask(parentNewTaskDtoWithOutTemplate));
    }

    @Test
    void saveTaskNegativeWithBadTitle() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(parent);
        when(userService.getUserByUid(any())).thenReturn(child);
        when(taskService.findByTitle(any())).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> parentTaskService.saveTask(parentNewTaskDtoWithTemplate));
    }

    @Test
    void saveTaskNegativeWithOutChilds() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(parent);
        when(userService.getUserByUid(any())).thenReturn(child);
        when(taskService.findByTitle(any())).thenReturn(task);
        when(userService.getAllByFamilyIdAndUserRole(any(), any())).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> parentTaskService.saveTask(parentNewTaskDtoWithTemplate));
    }

    @Test
    void confirmTaskPositive() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(parent);
        when(familyTaskService.findById(any())).thenReturn(familyTask);
        when(familyTaskService.save(any())).thenReturn(familyTask);
        when(firebaseService.sendMessage(any(),any(), any(), any())).thenReturn(true);

        boolean confirm = parentTaskService.confirmTask(confirmTaskDto);
        familyTask.setTaskStatus(TASK_STATUS.PENDING);
        assertTrue(confirm);
    }

    @Test
    void confirmTaskNegativeWithWrongFamilyTaskId() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(parent);
        when(familyTaskService.findById(any())).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> parentTaskService.confirmTask(confirmTaskDto));
    }

    @Test
    void confirmTaskNegativeWrongFamilyId() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(any())).thenReturn(userWithWrongFamilyId);
        when(familyTaskService.findById(any())).thenReturn(familyTask);

        assertThrows(BusinessException.class, () -> parentTaskService.confirmTask(confirmTaskDto));
    }

    @Test
    void confirmTaskNegativeWrongTaskStatus() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(any())).thenReturn(userWithWrongFamilyId);
        when(familyTaskService.findById(any())).thenReturn(familyTaskWithActiveStatus);

        assertThrows(BusinessException.class, () -> parentTaskService.confirmTask(confirmTaskDto));
    }

    @Test
    void rejectTaskPositive() {
        when(userService.getUserByUid(any())).thenReturn(parent);
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(familyTaskService.findById(any())).thenReturn(familyTask);
        when(familyTaskService.save(any())).thenReturn(familyTask);

        boolean reject = parentTaskService.rejectTask(1L);

        assertTrue(reject);
    }

    @Test
    void rejectTaskNegativeWrongFamilyId() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(any())).thenReturn(userWithWrongFamilyId);
        when(familyTaskService.findById(any())).thenReturn(familyTask);

        assertThrows(BusinessException.class, () -> parentTaskService.rejectTask(1L));
    }

    @Test
    void rejectTaskNegativeWrongTaskStatus() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(any())).thenReturn(userWithWrongFamilyId);
        when(familyTaskService.findById(any())).thenReturn(familyTaskWithActiveStatus);

        assertThrows(BusinessException.class, () -> parentTaskService.rejectTask(1L));
    }

    @Test
    void rejectTaskNegativeWithWrongFamilyTaskId() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getUserByUid(FIREBASE_ID)).thenReturn(parent);
        when(familyTaskService.findById(any())).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> parentTaskService.rejectTask(1L));
    }
}