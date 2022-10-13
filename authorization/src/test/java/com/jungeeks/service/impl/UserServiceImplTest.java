package com.jungeeks.service.impl;

import com.jungeeks.entity.SecurityUserFirebase;
import com.jungeeks.entity.User;
import com.jungeeks.repository.JpaUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = UserServiceImplTest.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    JpaUserRepository jpaUserRepository;

    private static User user;
    private static SecurityUserFirebase securityUserFirebaseWithSameEmail;
    private static SecurityUserFirebase securityUserFirebaseWithAnotherEmail;


    @BeforeAll
    static void setUp() {
        user = User.builder()
                .id(1L)
                .firebaseId("test")
                .email("test@gmail.com")
                .build();
        securityUserFirebaseWithSameEmail = SecurityUserFirebase.builder()
                .uid("test")
                .email("test@gmail.com")
                .build();
        securityUserFirebaseWithAnotherEmail = SecurityUserFirebase.builder()
                .uid("test")
                .email("anotherEmailTetst@gmail.com")
                .build();
    }

    @Test
    void checkUserWithSameEmails() {
        Mockito.when(jpaUserRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(user));

        userService.checkUser(securityUserFirebaseWithSameEmail);

        Mockito.verify(jpaUserRepository, Mockito.times(1)).findByFirebaseId("test");
        Mockito.verify(jpaUserRepository, Mockito.times(0)).save(user);
    }

    @Test
    void checkUserWithAnotherEmails() {
        Mockito.when(jpaUserRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(user));

        userService.checkUser(securityUserFirebaseWithAnotherEmail);

        Mockito.verify(jpaUserRepository, Mockito.times(1)).findByFirebaseId("test");

        user.setEmail(securityUserFirebaseWithAnotherEmail.getEmail());
        Mockito.verify(jpaUserRepository, Mockito.times(0)).save(user);
    }

    @Test
    void checkWithNullUserFromDb() {
        Mockito.when(jpaUserRepository.findByFirebaseId(any())).thenReturn(Optional.empty());

        userService.checkUser(securityUserFirebaseWithSameEmail);

        Mockito.verify(jpaUserRepository, Mockito.times(1)).findByFirebaseId("test");

        User userToSave = User.builder()
                .email(securityUserFirebaseWithSameEmail.getEmail())
                .firebaseId(securityUserFirebaseWithSameEmail.getUid())
                .build();

        Mockito.verify(jpaUserRepository, Mockito.times(1)).save(userToSave);
    }
}