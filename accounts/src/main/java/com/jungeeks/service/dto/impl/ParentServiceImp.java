package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.ChildDto;
import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.RewardRequest;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.REQUEST_STATUS;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.dto.ParentService;
import com.jungeeks.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("accounts_parentServiceImpl")
@Slf4j
public class ParentServiceImp implements ParentService {

    private UserService userService;
    private AuthorizationService authorizationService;

    @Autowired
    public void setUserService(@Qualifier("accounts_userServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAuthorizationService(@Qualifier("utils_authorizationServiceImpl")
                                            AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public ParentHomeDto getParentHomeDate() {
        User userDb = getUserFromAuth();
        log.debug("Request getParentHomeDate by user with uid {}", userDb.getFirebaseId());
        List<User> childs = userService.getAllByFamilyIdAndUserRole(userDb.getFamily().getId(), USER_ROLE.CHILD);

        log.debug("Number of childs {}", childs.size());
        List<ChildDto> childDtos = getChildDtoList(childs);
        return ParentHomeDto.builder()
                .familyId(userDb.getFamily().getId())
                .childDtos(childDtos)
                .build();
    }


    private List<ChildDto> getChildDtoList(List<User> childs) {
        log.debug("Mapping list child to list childDto");
        return childs.stream()
                .map((x) -> {
                    String[] split = x.getPhoto().size() == 0 ? null : x.getPhoto().get(0).getPath().split("/");
                    return (ChildDto.builder()
                            .name(x.getName())
                            .photoFileName(split == null ? null : split[split.length - 1])
                            .numberOfCompletedTasks(x.getTasks().stream()
                                    .filter(familyTask -> familyTask.getAuthor().getId().equals(x.getId())
                                            && familyTask.getTaskStatus() == TASK_STATUS.COMPLETED)
                                    .count())
                            .numberOfActiveTasks(x.getTasks().stream()
                                    .filter(familyTask -> familyTask.getAuthor().getId().equals(x.getId())
                                            && familyTask.getTaskStatus() == TASK_STATUS.ACTIVE)
                                    .count())
                            .rewardRequestIdsDtos(x.getFamily().getRequests().stream()
                                    .filter(request -> request.getUser().getId().equals(x.getId())
                                            && request.getRequestStatus() == REQUEST_STATUS.PENDING)
                                    .map(RewardRequest::getId)
                                    .toList())
                            .taskRequestIdsDtos(x.getTasks().stream()
                                    .filter(task -> Objects.equals(task.getAuthor().getId(), x.getId())
                                            && task.getTaskStatus() == TASK_STATUS.PENDING)
                                    .map(FamilyTask::getId)
                                    .toList())
                            .build());
                }).toList();
    }

    private User getUserFromAuth() {
        String uid = authorizationService.getUser().getUid();
        return userService.getUserByUid(uid);
    }
}
