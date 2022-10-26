package com.jungeeks.accounts.entity.impl;

import com.jungeeks.accounts.service.entity.impl.UserServiceImpl;
import com.jungeeks.entity.*;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.accounts.exception.PathNotFoundException;
import com.jungeeks.accounts.exception.UserNotFoundException;
import com.jungeeks.accounts.repository.UserRepository;
import com.jungeeks.service.photoStorage.PhotoStorageService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = UserServiceImplTest.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PhotoStorageService photoStorageService;

    @InjectMocks
    UserServiceImpl userService;

    static User user;
    static List<User> users;

    @BeforeAll
    static void prepareTestData() {
        user = User.builder()
                .id(1L)
                .email("testEmail")
//                .confirmationToken(ConfirmationToken.builder()
//                        .id(2L)
//                        .confirmedAt(LocalDateTime.now())
//                        .token("TestToken")
//                        .confirmedAt(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
//                        .createAt(LocalDateTime.of(2000, Month.MARCH, 2, 3, 4, 5, 6))
//                        .expiresAt(LocalDateTime.of(2001, Month.APRIL, 3, 4, 5, 6, 7))
//                        .build())
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
//                .confirmationToken(ConfirmationToken.builder()
//                        .id(3L)
//                        .confirmedAt(LocalDateTime.now())
//                        .token("TestToken1")
//                        .confirmedAt(LocalDateTime.of(2001, Month.DECEMBER, 1, 2, 3, 4, 5))
//                        .createAt(LocalDateTime.of(2001, Month.MARCH, 2, 3, 4, 5, 6))
//                        .expiresAt(LocalDateTime.of(2002, Month.APRIL, 3, 4, 5, 6, 7))
//                        .build())
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
        users = List.of(user, user2);
    }

    @Test
    void getUserByIdTestPositive() {
        Mockito.when(userRepository.findUserById(any()))
                .thenReturn(Optional.of(user));

        User userById = userService.getUserById(1L);

        assertEquals(user, userById);
    }

    @Test
    void getUserByIdTestNegative() {
        Mockito.when(userRepository.findUserById(any()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getAllByFamilyIdPositive() {
        Mockito.when(userRepository.findAllByFamilyId(any())).thenReturn(Optional.of(users));

        List<User> allByFamilyId = userService.getAllByFamilyId("123");
        assertEquals(users, allByFamilyId);
    }

    @Test
    void getAllByFamilyIdNegative() {
        Mockito.when(userRepository.findAllByFamilyId(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getAllByFamilyId("123"));
    }

    @Test
    void getUserPhotoPositive() throws IOException {
        File tmp = File.createTempFile("kidsAppTmpRead", "photo_tmp.jpg");
        Mockito.when(photoStorageService.load(any(), any())).thenReturn(tmp);

        File file = userService.getUserPhoto("path");

        assertEquals(tmp, file);
        assertEquals(tmp.length(), file.length());
        assertEquals(tmp.getName(), file.getName());
        assertEquals(tmp.canExecute(), file.canExecute());
        assertEquals(tmp.canRead(), file.canRead());
        assertEquals(tmp.canWrite(), file.canWrite());

        tmp.delete();
    }

    @Test
    void getUserPhotoNegative() throws IOException {
        File tmp = File.createTempFile("kidsAppTmpRead", "photo_tmp.jpg");
        tmp.setReadable(false);
        Mockito.when(photoStorageService.load(any(), any())).thenReturn(tmp);

        assertThrows(PathNotFoundException.class, () -> userService.getUserPhoto("path"));

        tmp.delete();
    }

}