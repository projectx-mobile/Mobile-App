package com.jungeeks.service.business.impl;

import com.jungeeks.entity.Family;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.entity.FamilyService;
import com.jungeeks.service.entity.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = RegisterUserServiceImplTest.class)
@Tag("unit")
class RegisterUserServiceImplTest {

    @InjectMocks
    private RegisterUserServiceImpl registerUserService;
    @Mock
    private UserService userService;
    @Mock
    private AuthorizationService authorizationService;
    @Mock
    private FamilyService familyService;

    private static SecurityUserFirebase securityUserFirebaseParent;
    private static User parent;
    private static User parentWithoutFamily;
    private static Family family;
    private static final String PARENT_FIREBASE_ID = "parentFI";
    private static final String USERNAME = "newUsername";
    private static final String FAMILY_ID = "familyIdTest";


    @BeforeAll
    static void setUp() {
        securityUserFirebaseParent = SecurityUserFirebase.builder()
                .uid(PARENT_FIREBASE_ID)
                .build();
        family = Family.builder()
                .id(FAMILY_ID)
                .build();
        parent = User.builder()
                .firebaseId(PARENT_FIREBASE_ID)
                .user_role(USER_ROLE.PARENT)
                .family(family)
                .build();
        parentWithoutFamily = User.builder()
                .firebaseId(PARENT_FIREBASE_ID)
                .user_role(USER_ROLE.PARENT)
                .build();;
    }
    @Test
    void registerByInvitePositive() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(familyService.getFamilyById(any())).thenReturn(family);
        when(userService.getUserByUid(PARENT_FIREBASE_ID)).thenReturn(parentWithoutFamily);

        boolean register = registerUserService.registerByInvite(USERNAME, FAMILY_ID, USER_ROLE.PARENT);

        assertTrue(register);
    }

    @Test
    void registerByInviteNegativeWithWrongFamilyId() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(familyService.getFamilyById(any())).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> registerUserService.registerByInvite(USERNAME, FAMILY_ID, USER_ROLE.PARENT));
    }

    @Test
    void registerByInviteNegativeWithWrongFirebaseId() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(familyService.getFamilyById(any())).thenReturn(family);
        when(userService.getUserByUid(PARENT_FIREBASE_ID)).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> registerUserService.registerByInvite(USERNAME, FAMILY_ID, USER_ROLE.PARENT));
    }

    @Test
    void registerByInviteNegativeExistUser() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(familyService.getFamilyById(FAMILY_ID)).thenReturn(family);
        when(userService.getUserByUid(PARENT_FIREBASE_ID)).thenReturn(parent);

        assertThrows(BusinessException.class, () -> registerUserService.registerByInvite(USERNAME, FAMILY_ID, USER_ROLE.PARENT));
    }

    @Test
    void registerParentUserPositive() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(familyService.save(family)).thenReturn(family);
        when(userService.getUserByUid(PARENT_FIREBASE_ID)).thenReturn(parentWithoutFamily);

        boolean register = registerUserService.registerParentUser(USERNAME);

        assertTrue(register);
    }

    @Test
    void registerParentUserNegativeWithWrongFirebaseId() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(familyService.save(family)).thenReturn(family);
        when(userService.getUserByUid(PARENT_FIREBASE_ID)).thenThrow(BusinessException.class);

        assertThrows(BusinessException.class, () -> registerUserService.registerParentUser(USERNAME));
    }

    @Test
    void registerParentUserNegativeExistUser() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(familyService.save(family)).thenReturn(family);
        when(userService.getUserByUid(PARENT_FIREBASE_ID)).thenReturn(parent);

        assertThrows(BusinessException.class, () -> registerUserService.registerParentUser(USERNAME));
    }
}