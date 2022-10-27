package com.jungeeks.service.dto.imp;

import com.jungeeks.dto.ChildDto;
import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.entity.*;
import com.jungeeks.entity.enums.*;
import com.jungeeks.repository.AccountsUserRepository;
import com.jungeeks.service.entity.imp.UserServiceImp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ParentServiceImpTest.class)
@Tag("unit")
class ParentServiceImpTest {

    @InjectMocks
    private ParentServiceImp parentServiceImp;
    @Mock
    private UserServiceImp userServiceImp;
    @Mock
    private AccountsUserRepository accountsUserRepository;

    private static User user;
    private static User child;
    private static List<User> users;
    private static ParentHomeDto parentHomeDto;
    private static ParentHomeDto parentHomeDtoWithOutChilds;
    private static List<User> childs;


    @BeforeAll
    static void prepareTestData() {
        user = User.builder()
                .id(1L)
                .email("testEmail")
                .user_role(USER_ROLE.PARENT)
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
                .name("Parent1")
                .family(Family.builder()
                        .id("qwerty123")
                        .requests(List.of(
                                RewardRequest.builder()
                                        .id(1L)
                                        .requestStatus(REQUEST_STATUS.PENDING)
                                        .reward(
                                                Reward.builder()
                                                        .id(1L)
                                                        .rewardStatus(REWARD_STATUS.UNLOCKED)
                                                        .points(123L)
                                                        .title("Sweets")
                                                        .photo(List.of(
                                                                Photo.builder()
                                                                        .creationDate(LocalDateTime.now())
                                                                        .path("photo3")
                                                                        .build(),
                                                                Photo.builder()
                                                                        .creationDate(LocalDateTime.now())
                                                                        .path("photo4")
                                                                        .build()))
                                                        .build())
                                        .build(),
                                RewardRequest.builder()
                                        .id(2L)
                                        .requestStatus(REQUEST_STATUS.PENDING)
                                        .reward(
                                                Reward.builder()
                                                        .id(2L)
                                                        .rewardStatus(REWARD_STATUS.UNLOCKED)
                                                        .points(1234L)
                                                        .title("Balloons")
                                                        .photo(List.of(
                                                                Photo.builder()
                                                                        .creationDate(LocalDateTime.now())
                                                                        .path("photo5")
                                                                        .build(),
                                                                Photo.builder()
                                                                        .creationDate(LocalDateTime.now())
                                                                        .path("photo6")
                                                                        .build()))
                                                        .build())
                                        .build()))
                        .build())
                .tasks(List.of(
                        FamilyTask.builder()
                                .id(1L)
                                .taskStatus(TASK_STATUS.PENDING)
                                .deadline(LocalDateTime.now())
                                .rewardPoints(198L)
                                .repeatable(true)
                                .photos(List.of(
                                        Photo.builder()
                                                .creationDate(LocalDateTime.now())
                                                .path("photo7")
                                                .build(),
                                        Photo.builder()
                                                .creationDate(LocalDateTime.now())
                                                .path("photo8")
                                                .build()))
                                .build(),
                        FamilyTask.builder()
                                .id(2L)
                                .taskStatus(TASK_STATUS.ACTIVE)
                                .deadline(LocalDateTime.now())
                                .rewardPoints(198L)
                                .repeatable(true)
                                .photos(List.of(
                                        Photo.builder()
                                                .creationDate(LocalDateTime.now())
                                                .path("photo7")
                                                .build(),
                                        Photo.builder()
                                                .creationDate(LocalDateTime.now())
                                                .path("photo8")
                                                .build()))
                                .build(),
                        FamilyTask.builder()
                                .id(3L)
                                .taskStatus(TASK_STATUS.COMPLETED)
                                .deadline(LocalDateTime.now())
                                .rewardPoints(198L)
                                .repeatable(true)
                                .photos(List.of(
                                        Photo.builder()
                                                .creationDate(LocalDateTime.now())
                                                .path("photo9")
                                                .build(),
                                        Photo.builder()
                                                .creationDate(LocalDateTime.now())
                                                .path("photo10")
                                                .build()))
                                .build()
                ))
                .build();
        child = User.builder()
                .id(4L)
                .email("childEmail1")
                .user_role(USER_ROLE.CHILD)
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
                .family(user.getFamily())
                .tasks(user.getTasks())
                .points(13L)
                .name("child1")
                .build();
        child.getFamily().getRequests().get(0).setUser(child);
        child.getFamily().getRequests().get(1).setUser(child);
        child.getTasks().get(0).setAuthor(child);
        child.getTasks().get(1).setAuthor(child);
        child.getTasks().get(2).setAuthor(child);

        users = List.of(user, child);

        parentHomeDto = ParentHomeDto.builder()
                .familyId(user.getFamily().getId())
                .childDtos(List.of(
                        ChildDto.builder()
                                .name(child.getName())
                                .photoFileName(child.getPhoto().get(0).getPath())
                                .numberOfActiveTasks(1L)
                                .numberOfCompletedTasks(1L)
                                .rewardRequestIdsDtos(List.of(1L, 2L))
                                .taskRequestIdsDtos(List.of(1L))
                                .build()
                ))
                .build();
        parentHomeDtoWithOutChilds = ParentHomeDto.builder()
                .familyId(user.getFamily().getId())
                .childDtos(List.of())
                .build();
        childs = List.of(child);
    }


    @Test
    void getParentHomeDatePositive() {
        when(userServiceImp.getAllByFamilyIdAndUserRole(user.getFamily().getId(), USER_ROLE.CHILD)).thenReturn(childs);

        ParentHomeDto parentHomeDto1 = parentServiceImp.getParentHomeDate(user);

        assertEquals(parentHomeDto1, parentHomeDto);
    }

    @Test
    void getParentHomeDateWithEmptyChildList() {
        when(userServiceImp.getAllByFamilyIdAndUserRole(user.getFamily().getId(), USER_ROLE.CHILD)).thenReturn(new ArrayList<>());

        ParentHomeDto parentHomeDto1 = parentServiceImp.getParentHomeDate(user);

        assertEquals(parentHomeDto1, parentHomeDtoWithOutChilds);
    }
}