package com.jungeeks.service.impl;

import com.jungeeks.dto.TaskDto;
import com.jungeeks.entity.User;
import com.jungeeks.exception.InvalidRequestException;
import com.jungeeks.exception.TaskNotFoundException;
import com.jungeeks.repository.FamilyTaskRepository;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.FamilyTaskService;
import com.jungeeks.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service("tasks_familyTaskServiceImpl")
public class FamilyTaskServiceImpl implements FamilyTaskService {
    private UserService userService;
    private AuthorizationService authorizationService;
    @Autowired
    private FamilyTaskRepository familyTaskRepository;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public List<TaskDto> getAllTasks() {
        return null;
    }

    @Override
    public List<TaskDto> getAllTasksByUserId(Long userId) {
        String uid = authorizationService.getUser().getUid();
//        User authUser = userService.getUserByUid(uid);
        String authUserFamilyId = authUser.getFamily().getId();
        User user = userService.getUserById(userId);
        if (Objects.isNull(user) || !user.getFamily().getId().equals(authUserFamilyId)) {
            log.warn("Invalid id param");
            throw new InvalidRequestException("Invalid id parameter");
        }
        return familyTaskRepository.findAllByFamilyId(user.getFamily().getId())
                .orElseThrow(() -> new TaskNotFoundException(String.format("User with id %s not found", user.getFamily().getId())));

    }

    @Override
    public List<TaskDto> getAllTasksByCurrentUserUid() {
        String uid = authorizationService.getUser().getUid();
//        User user = userService.getUserByUid(uid);
        return familyTaskRepository.findAllByFamilyId(user.getFamily().getId())
                .orElseThrow(() -> new TaskNotFoundException(String.format("User with id %s not found", user.getFamily().getId())));
    }

}
