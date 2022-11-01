package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.*;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.AccountsUserRepository;
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
import com.sun.xml.bind.v2.TODO;
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


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Tag("unit")
@SpringBootTest(classes = UserServiceImplTest.class)
class UserServiceImplTest {

    @Mock
    private AccountsUserRepository accountsUserRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private static User TestDataUser;
    private static List<User> TestDataUsers;

    @BeforeAll
    static void prepareTestData() {
        TestDataUser = User.builder()
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

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private static List<NotificationResponse> notificationResponsesTest;
    private static List<TaskResponse> taskResponseTest;
    private static User user;
    private static User parent;
    private static List<User> users;

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
        parent = User.builder()
                .id(1L)
                .family(Family.builder()
                        .id("1L")
                        .build())
                .email("test@gmail.com")
                .user_role(USER_ROLE.PARENT)
                .build();
        users = new ArrayList<>(List.of(parent));
    }

    @Test
    void getUserById() {
        when(userRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(user));

        User user1 = userService.getUserByFirebaseId("");

        assertEquals(user1, user);
    }

    @Test
    void canGetNoUserById() {
        when(userRepository.findUserById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByFirebaseId("1L"));
    }

    @Test
    void getDeadlineOfAllTask() {
        List<NotificationResponse> notificationResponses = userService.getDeadlineOfAllTask(user);

        assertEquals(notificationResponses,notificationResponsesTest);
    }

    @Test
    void getUserTaskById() {
        List<TaskResponse> taskResponse = userService.getUserTaskById(user);

        assertEquals(taskResponse,taskResponseTest);
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
        TestDataUsers = List.of(TestDataUser, user2);
    }

    @Test
    void getUserByIdTestPositive() {
        when(accountsUserRepository.findUserById(any()))
                .thenReturn(Optional.of(TestDataUser));

        User userById = userService.getUserById(1L);

        assertEquals(TestDataUser, userById);
    }

    @Test
    void getUserByIdTestNegative() {
        when(accountsUserRepository.findUserById(any()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(1L),
                "User with id 1 not found");
    }

    @Test
    void getUserByUidPositive(){
        when(accountsUserRepository.findByFirebaseId(any()))
                .thenReturn(Optional.of(TestDataUser));
        assertEquals(TestDataUser,userService.getUserByUid(any()));
    }

    @Test
    void getUserByUidNegative(){
        when(accountsUserRepository.findByFirebaseId(any()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserByUid("qwerty"),
                "User with uid qwerty not found");
    }

    @Test
    void getAllByFamilyIdPositive() {
        when(accountsUserRepository.findAllByFamilyId(any())).thenReturn(Optional.of(TestDataUsers));

        List<User> allByFamilyId = userService.getAllByFamilyId("123");
        assertEquals(TestDataUsers, allByFamilyId);
        assertEquals("123", users.get(0).getFamily().getId());//FIXME:fix
    }

    @Test
    void getAllByFamilyIdNegative() {
        when(accountsUserRepository.findAllByFamilyId(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getAllByFamilyId("123"));
    }

    @Test
    void changeUserStatusPositive(){
        when(accountsUserRepository.findByFirebaseId(any()))
                .thenReturn(Optional.of(new User()));
        assertDoesNotThrow(() -> userService.changeUserStatus("qwerty",USER_STATUS.ACTIVE));

    }

    @Test
    void changeUserStatusNegative(){
        when(accountsUserRepository.findByFirebaseId(any()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.changeUserStatus("qwerty",USER_STATUS.ACTIVE),
                "User with uid qwerty not found");
    }

        when(userRepository.findAllByFamilyId(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getAllByFamilyId("1L"));
    }

    @Test
    void getAllByFamilyIdAndUserRolePositive() {
        when(userRepository.findAllByFamilyIdAndUser_role("1L", USER_ROLE.PARENT)).thenReturn(Optional.ofNullable(users));

        List<User> allByFamilyIdAndUserRole = userService.getAllByFamilyIdAndUserRole("1L", USER_ROLE.PARENT);

        assertEquals(allByFamilyIdAndUserRole, users);
        assertEquals(allByFamilyIdAndUserRole.get(0).getUser_role(), USER_ROLE.PARENT);
    }

    @Test
    void getAllByFamilyIdAndUserRoleNegative() {
        when(userRepository.findAllByFamilyIdAndUser_role(any(), any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getAllByFamilyIdAndUserRole("1L", USER_ROLE.PARENT));
    }

    @Test
    void getUserByFirebaseIdPositive() {
        when(userRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(parent));

        User userByFirebaseId = userService.getUserByFirebaseId(any());

        assertEquals(userByFirebaseId, parent);
        assertEquals(userByFirebaseId.getFirebaseId(), parent.getFirebaseId());
    }

    @Test
    void getUserByFirebaseIdNegative() {
        when(userRepository.findByFirebaseId(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByFirebaseId(any()));
    }
}