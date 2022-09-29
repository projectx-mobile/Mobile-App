package com.jungeeks.service.entity.impl;

import com.jungeeks.entitiy.*;
import com.jungeeks.entitiy.enums.TASK_STATUS;
import com.jungeeks.entitiy.enums.USER_ROLE;
import com.jungeeks.entitiy.enums.USER_STATUS;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.photoStorage.impl.PhotoStorageServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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
    PhotoStorageServiceImpl photoStorageService;

    @InjectMocks
    UserServiceImpl userService;

    static User user;

    @BeforeAll
    static void prepareData() {
        user = User.builder()
                .id(1L)
                .email("testEmail")
                .confirmationToken(ConfirmationToken.builder()
                        .id(2L)
                        .confirmedAt(LocalDateTime.now())
                        .token("TestToken")
                        .confirmedAt(LocalDateTime.of(2000, Month.DECEMBER, 1, 2, 3, 4, 5))
                        .createAt(LocalDateTime.of(2000, Month.MARCH, 2, 3, 4, 5, 6))
                        .expiresAt(LocalDateTime.of(2001, Month.APRIL, 3, 4, 5, 6, 7))
                        .build())
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
                                .daily(true)
                                .deadline(LocalDateTime.now())
                                .points(676L)
                                .id(1L)
                                .taskStatus(TASK_STATUS.COMPLETED)
                                .build(),
                        FamilyTask.builder()
                                .daily(false)
                                .deadline(LocalDateTime.now())
                                .points(633L)
                                .id(2L)
                                .taskStatus(TASK_STATUS.PENDING)
                                .build()))
                .build();
    }

    @Test
    void getUserById() {
        Mockito.when(userRepository.findUserById(any()))
                .thenReturn(Optional.of(user));

        User userById = userService.getUserById(1L);

        assertEquals(user,userById);

    }

    @Test
    void getAllByFamilyId() {
    }

    @Test
    void getUserPhoto() {
    }
}