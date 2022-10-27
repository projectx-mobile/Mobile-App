package com.jungeeks.accounts.dto.impl;

import com.jungeeks.accounts.dto.FamilyMemberDto;
import com.jungeeks.accounts.dto.UserInfoDto;
import com.jungeeks.accounts.service.dto.impl.UserInfoServiceImpl;
import com.jungeeks.entity.*;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.accounts.service.dto.FamilyMemberService;
import com.jungeeks.accounts.service.entity.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@Tag("unit")
@SpringBootTest(classes = UserInfoServiceImplTest.class)
class UserInfoServiceImplTest {

    @Mock
    UserService userService;

    @Mock
    FamilyMemberService familyMemberService;

    @InjectMocks
    UserInfoServiceImpl userInfoService;

    static User user;
    static UserInfoDto userInfoDto;

    static List<FamilyMemberDto> familyMembers;

    @BeforeAll
    static void prepareTestData() {

        user = User.builder()
                .name("Dev")
                .email("dev123@gmail.com")
                .user_status(USER_STATUS.ACTIVE)
                .photo(List.of(Photo.builder()
                        .path("devPhoto")
                        .creationDate(LocalDateTime.now())
                        .build()))
                .family(Family.builder()
                        .id("familyId")
                        .build())
                .build();

        familyMembers = List.of(FamilyMemberDto.builder()
                        .id(1L)
//                        .photoPath("photo1")
                        .username("Dev1")
                        .build(),
                FamilyMemberDto.builder()
                        .id(2L)
//                        .photoPath("photo2")
                        .username("Dev2")
                        .build());

        userInfoDto = UserInfoDto.builder()
                .username(user.getName())
//                .email(user.getEmail())
//                .userStatus(user.getUser_status())
//                .signUpType(
//                        SIGN_UP_TYPE.APPLE//FIX this (get this value from SpringSecurity)
//                )
                .familyMembers(familyMembers)
//                .photoPath(user.getPhoto().get(0).getPath())
                .build();
    }

    @Test
    void getUserInfoByUserId() {
        Mockito.when(userService.getUserById(any())).thenReturn(user);
        Mockito.when(familyMemberService.getFamilyMembers(any())).thenReturn(familyMembers);

        UserInfoDto userInfoByUserId = userInfoService.getUserInfoByUserId(1L);

        assertEquals(userInfoDto,userInfoByUserId);
    }
}