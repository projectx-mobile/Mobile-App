package com.jungeeks.service.entity.imp;

import com.jungeeks.entity.Family;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.AccountsUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UserServiceImpTest.class)
@Tag("unit")
class UserServiceImpTest {

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Mock
    private AccountsUserRepository accountsUserRepository;

    private static User user;

    private static List<User> users;

    @BeforeAll
    static void setUp() {
        user = User.builder()
                .id(1L)
                .family(Family.builder()
                        .id("1L")
                        .build())
                .email("test@gmail.com")
                .user_role(USER_ROLE.PARENT)
                .build();
        users = new ArrayList<>(List.of(user));
    }

    @Test
    void getUserByIdPositive() {
        when(accountsUserRepository.findUserById(any())).thenReturn(Optional.ofNullable(user));

        User userById = userServiceImp.getUserById(any());
        assertEquals(userById, user);
        assertEquals(userById.getId(), user.getId());
    }

    @Test
    void getUserByIdNegative() {
        when(accountsUserRepository.findUserById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImp.getUserById(any()));
    }

    @Test
    void getAllByFamilyIdPositive() {
        when(accountsUserRepository.findAllByFamilyId(any())).thenReturn(Optional.ofNullable(users));

        List<User> familyList = userServiceImp.getAllByFamilyId("1L");
        assertEquals(familyList, users);
        assertEquals(familyList.get(0).getFamily().getId(), users.get(0).getFamily().getId());
    }

    @Test
    void getAllByFamilyIdNegative() {
        when(accountsUserRepository.findAllByFamilyId(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImp.getAllByFamilyId("1L"));
    }

    @Test
    void getAllByFamilyIdAndUserRolePositive() {
        when(accountsUserRepository.findAllByFamilyIdAndUser_role(any(), any())).thenReturn(Optional.ofNullable(users));

        List<User> allByFamilyIdAndUserRole = userServiceImp.getAllByFamilyIdAndUserRole("1L", USER_ROLE.PARENT);
        assertEquals(allByFamilyIdAndUserRole, users);
        assertEquals(allByFamilyIdAndUserRole.get(0).getUser_role(), USER_ROLE.PARENT);
    }

    @Test
    void getAllByFamilyIdAndUserRoleNegative() {
        when(accountsUserRepository.findAllByFamilyIdAndUser_role(any(), any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImp.getAllByFamilyIdAndUserRole("1L", USER_ROLE.PARENT));
    }

    @Test
    void getUserByFirebaseIdPositive() {
        when(accountsUserRepository.findByFirebaseId(any())).thenReturn(Optional.ofNullable(user));

        User userByFirebaseId = userServiceImp.getUserByFirebaseId(any());

        assertEquals(userByFirebaseId, user);
        assertEquals(userByFirebaseId.getFirebaseId(), user.getFirebaseId());
    }

    @Test
    void getUserByFirebaseIdNegative() {
        when(accountsUserRepository.findByFirebaseId(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userServiceImp.getUserByFirebaseId(any()));

    }
}