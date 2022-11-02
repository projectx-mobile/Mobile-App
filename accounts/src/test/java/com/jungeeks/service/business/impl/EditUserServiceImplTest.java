package com.jungeeks.service.business.impl;

import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.exception.NotEnoughRightsException;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.entity.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest(classes = EditUserServiceImplTest.class)
@Tag("unit")
class EditUserServiceImplTest {

    @InjectMocks
    private EditUserServiceImpl editUserService;
    @Mock
    private UserService userService;
    @Mock
    private AuthorizationService authorizationService;

    private static SecurityUserFirebase securityUserFirebaseParent;
    private static SecurityUserFirebase securityUserFirebaseChild;
    private static User user;
    private static User child;

    private static final String PARENT_FIREBASE_ID = "parentFI";
    private static final String CHILD_FIREBASE_ID = "childFI";

    @BeforeAll
    static void setUp() {
        securityUserFirebaseParent = SecurityUserFirebase.builder()
                .uid(PARENT_FIREBASE_ID)
                .build();
        securityUserFirebaseChild = SecurityUserFirebase.builder()
                .uid(CHILD_FIREBASE_ID)
                .build();
        user = User.builder()
                .firebaseId(PARENT_FIREBASE_ID)
                .user_role(USER_ROLE.PARENT)
                .build();
        child = User.builder()
                .firebaseId(CHILD_FIREBASE_ID)
                .user_role(USER_ROLE.CHILD)
                .build();
    }
    @Test
    void changeUserNamePositive() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(userService.changeUserName(PARENT_FIREBASE_ID, "test")).thenReturn(true);

        boolean change = editUserService.changeUserName("test");

        assertTrue(change);
        verify(userService, times(1)).changeUserName(PARENT_FIREBASE_ID, "test");
    }

    @Test
    void changeUserNameNegative() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(userService.changeUserName(PARENT_FIREBASE_ID, "test")).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> editUserService.changeUserName("test"));

        verify(userService, times(1)).changeUserName(PARENT_FIREBASE_ID, "test");
    }

    @Test
    void changeUserStatusPositive() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(userService.changeUserStatus(PARENT_FIREBASE_ID, USER_STATUS.REMOVED)).thenReturn(true);

        boolean change = editUserService.changeUserStatus(USER_STATUS.REMOVED);

        assertTrue(change);
        verify(userService, times(1)).changeUserStatus(PARENT_FIREBASE_ID, USER_STATUS.REMOVED);
    }

    @Test
    void changeUserStatusNegative() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(userService.changeUserStatus(PARENT_FIREBASE_ID, USER_STATUS.REMOVED)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> editUserService.changeUserStatus(USER_STATUS.REMOVED));

        verify(userService, times(1)).changeUserStatus(PARENT_FIREBASE_ID, USER_STATUS.REMOVED);
    }

    @Test
    void deleteFamilyMemberPositive() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(userService.getUserByUid(PARENT_FIREBASE_ID)).thenReturn(user);
        when(userService.deleteFamilyMember(1L)).thenReturn(true);

        boolean delete = editUserService.deleteFamilyMember(1L);

        assertTrue(delete);
        verify(userService, times(1)).deleteFamilyMember(any());
    }

    @Test
    void deleteFamilyMemberNegativeWithWrongUserRole() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseChild);
        when(userService.getUserByUid(CHILD_FIREBASE_ID)).thenReturn(child);

        assertThrows(NotEnoughRightsException.class, () -> editUserService.deleteFamilyMember(1L));

        verify(userService, times(0)).deleteFamilyMember(any());
    }

    @Test
    void deleteFamilyMemberNegativeWithWrongUserId() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebaseParent);
        when(userService.getUserByUid(PARENT_FIREBASE_ID)).thenReturn(user);
        when(userService.deleteFamilyMember(1L)).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> editUserService.deleteFamilyMember(1L));

        verify(userService, times(1)).deleteFamilyMember(any());
    }
}