package com.jungeeks.service.entity.impl;

import com.jungeeks.exception.BusinessException;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.aws.service.photoStorage.PhotoStorageService;
import com.jungeeks.entity.Photo;
import com.jungeeks.entity.User;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Tag("unit")
@SpringBootTest(classes = UserPhotoServiceImplTest.class)
class UserPhotoServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private PhotoStorageService photoStorageService;

    @Mock
    private AuthorizationService authorizationService;

    @InjectMocks
    private UserPhotoServiceImpl userPhotoService;

    private static User testDataValidUser;
    private static final String path1 = "path1";
    private static final String path2 = "path2";
    private static final String path3 = "path3";
    private static final String path4 = "path4";
    private static File validFile;
    private static File notValidFile;

    @BeforeAll
    static void prepare() throws IOException {
        testDataValidUser = User.builder()
                .id(1L)
                .photo(List.of(
                        Photo.builder()
                                .path(path1)
                                .build(),
                        Photo.builder()
                                .path(path2)
                                .build(),
                        Photo.builder()
                                .path(path3)
                                .build(),
                        Photo.builder()
                                .path(path4)
                                .build()
                ))
                .build();

        validFile = File.createTempFile("prefix", "suffix");
        notValidFile = new File("notValidFile.h");
    }

    @Test
    void getUserPhotoByPathPositive() {
        when(authorizationService.getUser()).thenReturn(SecurityUserFirebase.builder()
                .uid("123")
                .build());
        when(userService.getUserByUid(any())).thenReturn(testDataValidUser);

        when(photoStorageService.load(any(), any())).thenReturn(validFile);
        assertDoesNotThrow(() -> userPhotoService.getUserPhoto(path1));

    }

    @Test
    void getUserPhotoByPathNegativeNotValidPath() {
        when(authorizationService.getUser()).thenReturn(SecurityUserFirebase.builder()
                .uid("123")
                .build());
        when(userService.getUserByUid(any())).thenReturn(testDataValidUser);

        when(photoStorageService.load(any(), any())).thenReturn(validFile);
        assertThrows(BusinessException.class,
                () -> userPhotoService.getUserPhoto("123456789"),
                "Path not found");
    }

    @Test
    void getUserPhotoByPathNegativeFileNotExistPath() {
        when(authorizationService.getUser()).thenReturn(SecurityUserFirebase.builder()
                .uid("123")
                .build());
        when(userService.getUserByUid(any())).thenReturn(testDataValidUser);
        when(photoStorageService.load(any(), any())).thenReturn(notValidFile);

        assertThrows(BusinessException.class,
                () -> userPhotoService.getUserPhoto(path1),
                "Photo not found");

    }

    @Test
    void getUserPhotoByPathAndUserIdPositive() {
        when(userService.getUserById(any())).thenReturn(testDataValidUser);

        when(photoStorageService.load(any(), any())).thenReturn(validFile);
        assertDoesNotThrow(() -> userPhotoService.getUserPhoto(testDataValidUser.getId(), path1));

    }

    @Test
    void getUserPhotoByPathAndUserIdNegativeNotValidPath() {
        when(userService.getUserById(any())).thenReturn(testDataValidUser);

        when(photoStorageService.load(any(), any())).thenReturn(validFile);
        assertThrows(BusinessException.class,
                () -> userPhotoService.getUserPhoto(testDataValidUser.getId(), "123456789"),
                "Path not found");
    }

    @Test
    void getUserPhotoByPathAndUserIdNegativeFileNotExistPath() {
        when(userService.getUserById(any())).thenReturn(testDataValidUser);
        when(photoStorageService.load(any(), any())).thenReturn(notValidFile);

        assertThrows(BusinessException.class,
                () -> userPhotoService.getUserPhoto(testDataValidUser.getId(), path1),
                "Photo not found");

    }
}