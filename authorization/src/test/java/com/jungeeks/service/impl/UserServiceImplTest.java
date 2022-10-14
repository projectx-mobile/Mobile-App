package com.jungeeks.service.impl;

import com.jungeeks.entity.SecurityUserFirebase;
import com.jungeeks.entity.User;
import com.jungeeks.exception.RegistrationFailedException;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.SecurityService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserServiceImplTest.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    SecurityService securityService;

    private static User user;
    private static User userWithoutClientApps;
    private static SecurityUserFirebase securityUserFirebaseWithSameEmail;
    private static SecurityUserFirebase securityUserFirebaseWithAnotherEmail;

    private static final String FIREBASE_ID = "test";


    @BeforeAll
    static void setUp() {
        user = User.builder()
                .id(1L)
                .firebaseId(FIREBASE_ID)
                .email("test@gmail.com")
                .clientApps(new ArrayList<>())
                .build();
        userWithoutClientApps = User.builder()
                .id(2L)
                .firebaseId(FIREBASE_ID)
                .email("test1@gmail.com")
                .build();
        securityUserFirebaseWithSameEmail = SecurityUserFirebase.builder()
                .uid(FIREBASE_ID)
                .email("test@gmail.com")
                .build();
        securityUserFirebaseWithAnotherEmail = SecurityUserFirebase.builder()
                .uid(FIREBASE_ID)
                .email("anotherEmailTetst@gmail.com")
                .build();
    }

    @Test
    void checkUserWithSameEmails() {
        Mockito.when(userRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(user));

        userService.checkUser(securityUserFirebaseWithSameEmail);

        Mockito.verify(userRepository, Mockito.times(1)).findByFirebaseId(FIREBASE_ID);
        Mockito.verify(userRepository, Mockito.times(0)).save(user);
    }

    @Test
    void checkUserWithAnotherEmails() {
        Mockito.when(userRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(user));

        userService.checkUser(securityUserFirebaseWithAnotherEmail);

        Mockito.verify(userRepository, Mockito.times(1)).findByFirebaseId(FIREBASE_ID);

        user.setEmail(securityUserFirebaseWithAnotherEmail.getEmail());
        Mockito.verify(userRepository, Mockito.times(0)).save(user);
    }

    @Test
    void checkWithNullUserFromDb() {
        Mockito.when(userRepository.findByFirebaseId(any())).thenReturn(Optional.empty());

        userService.checkUser(securityUserFirebaseWithSameEmail);

        Mockito.verify(userRepository, Mockito.times(1)).findByFirebaseId(FIREBASE_ID);

        User userToSave = User.builder()
                .email(securityUserFirebaseWithSameEmail.getEmail())
                .firebaseId(securityUserFirebaseWithSameEmail.getUid())
                .build();

        Mockito.verify(userRepository, Mockito.times(1)).save(userToSave);
    }

    @Test
    void updateAppRegistrationTokenExistsUser() {
        Mockito.when(userRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(user));
        Mockito.when(securityService.getUser()).thenReturn(securityUserFirebaseWithSameEmail);

        boolean update = userService.updateAppRegistrationToken("newToken");

        assertTrue(update);
    }

    @Test
    void updateAppRegistrationTokenNotExistsUser() {
        Mockito.when(userRepository.findByFirebaseId(any())).thenReturn(Optional.empty());
        Mockito.when(securityService.getUser()).thenReturn(securityUserFirebaseWithSameEmail);

        assertThrows(RegistrationFailedException.class, () -> userService.updateAppRegistrationToken("0"));
    }

    @Test
    void checkUserByContainsRegistrationTokenExistsUser() {
        Mockito.when(userRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(user));
        Mockito.when(securityService.getUser()).thenReturn(securityUserFirebaseWithSameEmail);

        boolean clientApps = userService.checkUserByContainsRegistrationToken();

        assertTrue(clientApps);
    }

    @Test
    void checkUserByContainsRegistrationTokenNotExistsUser() {
        Mockito.when(userRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(userWithoutClientApps));
        Mockito.when(securityService.getUser()).thenReturn(securityUserFirebaseWithSameEmail);

        boolean clientApps = userService.checkUserByContainsRegistrationToken();

        assertFalse(clientApps);
    }
}