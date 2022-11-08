package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.*;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.repository.AccountsUserRepository;
import com.jungeeks.entity.Family;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.Task;
import com.jungeeks.entity.User;
import com.jungeeks.dto.NotificationDto;
import com.jungeeks.dto.TaskDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


import java.time.Month;
import java.util.ArrayList;


@Tag("unit")
@SpringBootTest(classes = UserServiceImplTest.class)
class UserServiceImplTest {

    @Mock
    private AccountsUserRepository accountsUserRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private static User testDataUser;
    private static List<User> testDataUsers;

    private static List<NotificationDto> notificationResponsesTest;
    private static List<TaskDto> taskDtoTest;
    private static User user;
    private static User parent;
    private static User parentWithAdmin;
    private static List<User> users;




    @BeforeAll
    static void prepareTestData() {
        testDataUser = User.builder()
                .id(1L)
                .email("testEmail")
                .user_role(USER_ROLE.ADMIN)
                .user_status(USER_STATUS.ACTIVE)
                .photo(List.of(
                        Photo.builder()
                                .creationDate(LocalDateTime.now())
                                .path("photo1")
                                .build(),
                        Photo.builder()
                                .creationDate(LocalDateTime.now())
                                .path("photo2")
                                .build()))
                .points(13L)
                .name("Dev")
                .family(Family.builder()
                        .id("qwerty123")
                        .build())
                .tasks(List.of(
                        FamilyTask.builder()
                                .repeatable(true)
                                .deadline(LocalDateTime.now())
                                .rewardPoints(676L)
                                .id(1L)
                                .taskStatus(TASK_STATUS.COMPLETED)
                                .build(),
                        FamilyTask.builder()
                                .repeatable(true)
                                .deadline(LocalDateTime.now())
                                .rewardPoints(633L)
                                .id(2L)
                                .taskStatus(TASK_STATUS.PENDING)
                                .build()))
                .build();
        User user2 = User.builder()
                .id(4L)
                .email("testEmail1")
                .user_role(USER_ROLE.ADMIN)
                .user_status(USER_STATUS.ACTIVE)
                .photo(List.of(
                        Photo.builder()
                                .creationDate(LocalDateTime.now())
                                .path("photo3")
                                .build(),
                        Photo.builder()
                                .creationDate(LocalDateTime.now())
                                .path("photo4")
                                .build()))
                .points(13L)
                .name("Dev1")
                .family(Family.builder()
                        .id("qwerty1234")
                        .build())
                .tasks(List.of(
                        FamilyTask.builder()
                                .repeatable(true)
                                .deadline(LocalDateTime.now())
                                .rewardPoints(676L)
                                .id(1L)
                                .taskStatus(TASK_STATUS.COMPLETED)
                                .build(),
                        FamilyTask.builder()
                                .repeatable(true)
                                .deadline(LocalDateTime.now())
                                .rewardPoints(633L)
                                .id(2L)
                                .taskStatus(TASK_STATUS.PENDING)
                                .build()))
                .build();
        testDataUsers = List.of(testDataUser, user2);

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

        notificationResponsesTest = List.of(
                NotificationDto.builder()
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build(),
                NotificationDto.builder()
                        .localDateTime(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .build()
        );

        taskDtoTest = List.of(
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
        parent = User.builder()
                .id(2L)
                .family(Family.builder()
                        .id("1L")
                        .build())
                .email("test@gmail.com")
                .user_role(USER_ROLE.PARENT)
                .build();
        parentWithAdmin = User.builder()
                .id(3L)
                .family(Family.builder()
                        .id("1L")
                        .build())
                .email("test@gmail.com")
                .user_role(USER_ROLE.ADMIN)
                .build();
        users = new ArrayList<>(List.of(parent, parentWithAdmin));

    }


    @Test
    void getUserById() {
        when(accountsUserRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(user));

        User user1 = userService.getUserByUid("");

        assertEquals(user1, user);
    }

    @Test
    void notGetUserById() {
        when(accountsUserRepository.findUserById(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.getUserByUid("1L"));
    }

    @Test
    void getUserByIdPositive() {
        when(accountsUserRepository.findUserById(any()))
                .thenReturn(Optional.of(testDataUser));

        User userById = userService.getUserById(1L);

        assertEquals(testDataUser, userById);
    }

    @Test
    void getUserByIdNegative() {
        when(accountsUserRepository.findUserById(any()))
                .thenReturn(Optional.empty());

        assertThrows(BusinessException.class,
                () -> userService.getUserById(1L),
                "User not found");
    }

    @Test
    void getUserByUidPositive() {
        when(accountsUserRepository.findByFirebaseId(any()))
                .thenReturn(Optional.of(testDataUser));
        assertEquals(testDataUser, userService.getUserByUid(any()));
    }

    @Test
    void getUserByUidNegative() {
        when(accountsUserRepository.findByFirebaseId(any()))
                .thenReturn(Optional.empty());

        assertThrows(BusinessException.class,
                () -> userService.getUserByUid("qwerty"),
                "User not found");
    }

    @Test
    void getAllByFamilyIdPositive() {
        when(accountsUserRepository.findAllByFamilyId(any())).thenReturn(Optional.of(testDataUsers));

        List<User> allByFamilyId = userService.getAllByFamilyId("123");
        assertEquals(testDataUsers, allByFamilyId);
        assertEquals("qwerty123", testDataUsers.get(0).getFamily().getId());
    }

    @Test
    void getAllByFamilyIdNegative() {
        when(accountsUserRepository.findAllByFamilyId(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.getAllByFamilyId("123"));
    }

    @Test
    void changeUserStatusPositive() {
        when(accountsUserRepository.findByFirebaseId(any()))
                .thenReturn(Optional.of(new User()));
        assertDoesNotThrow(() -> userService.changeUserStatus("qwerty", USER_STATUS.ACTIVE));
    }

    @Test
    void changeUserStatusNegative() {
        when(accountsUserRepository.findByFirebaseId(any()))
                .thenReturn(Optional.empty());

        assertThrows(BusinessException.class,
                () -> userService.changeUserStatus("qwerty", USER_STATUS.ACTIVE),
                "User not found");
    }

    @Test
    void getAllByFamilyIdAndUserRoleAndUserStatusPositive() {
        when(accountsUserRepository.findAllByFamilyIdAndUser_roleAndUser_status("1L", USER_ROLE.PARENT, USER_STATUS.ACTIVE)).
                thenReturn(Optional.ofNullable(users));

        List<User> allByFamilyIdAndUserRole = userService.getAllByFamilyIdAndUserRoleAndUserStatus("1L", USER_ROLE.PARENT, USER_STATUS.ACTIVE);

        assertEquals(allByFamilyIdAndUserRole, users);
        assertEquals(allByFamilyIdAndUserRole.get(0).getUser_role(), USER_ROLE.PARENT);
    }

    @Test
    void getAllByFamilyIdAndUserRoleAndUserStatusNegative() {
        when(accountsUserRepository.findAllByFamilyIdAndUser_roleAndUser_status(any(), any(), any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.getAllByFamilyIdAndUserRoleAndUserStatus("1L", USER_ROLE.PARENT, USER_STATUS.ACTIVE));
    }

    @Test
    void getUserByFirebaseIdPositive() {
        when(accountsUserRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(parent));

        User userByFirebaseId = userService.getUserByUid(any());

        assertEquals(userByFirebaseId, parent);
        assertEquals(userByFirebaseId.getFirebaseId(), parent.getFirebaseId());
    }

    @Test
    void getUserByFirebaseIdNegative() {
        when(accountsUserRepository.findByFirebaseId(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.getUserByUid(any()));
    }

    @Test
    void getAllByFamilyIdAndUserRolePositive() {
        when(accountsUserRepository.findAllByFamilyIdAndUser_role("1L", USER_ROLE.PARENT)).
                thenReturn(Optional.ofNullable(users));

        List<User> allByFamilyIdAndUserRole = userService.getAllByFamilyIdAndUserRole("1L", USER_ROLE.PARENT);

        assertEquals(allByFamilyIdAndUserRole, users);
        assertEquals(allByFamilyIdAndUserRole.get(0).getUser_role(), USER_ROLE.PARENT);
    }

    @Test
    void getAllByFamilyIdAndUserRoleNegative() {
        when(accountsUserRepository.findAllByFamilyIdAndUser_role(any(), any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.getAllByFamilyIdAndUserRole("1L", USER_ROLE.PARENT));
    }

    @Test
    void getAllByFamilyIdAndUserRoleWithAdminPositive() {
        when(accountsUserRepository.findAllByFamilyIdAndUser_roleWithAdmin("1L", USER_ROLE.PARENT)).
                thenReturn(Optional.ofNullable(users));

        List<User> allByFamilyIdAndUserRole = userService.getAllByFamilyIdAndUserRoleWithAdmin("1L", USER_ROLE.PARENT);

        assertEquals(allByFamilyIdAndUserRole, users);
        assertEquals(allByFamilyIdAndUserRole.get(1).getUser_role(), USER_ROLE.ADMIN);
    }

    @Test
    void getAllByFamilyIdAndUserRoleWithAdminNegative() {
        when(accountsUserRepository.findAllByFamilyIdAndUser_roleWithAdmin(any(), any())).
                thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> userService.getAllByFamilyIdAndUserRoleWithAdmin("1L", USER_ROLE.PARENT));
    }
}