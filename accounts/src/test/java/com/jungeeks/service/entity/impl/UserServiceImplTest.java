package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.*;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.AccountsUserRepository;
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

}