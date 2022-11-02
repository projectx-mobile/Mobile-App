package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.NotificationDto;
import com.jungeeks.dto.TaskDto;
import com.jungeeks.entity.User;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.dto.ChildService;
import com.jungeeks.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class ChildServiceImpl implements ChildService {

    private AuthorizationService authorizationService;
    private UserService userService;

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Autowired
    @Qualifier("accounts_userServiceImpl")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<NotificationDto> getDeadlineOfAllTask() {
        User child = getUser();
        log.debug("Request getDeadlineOfAllTask by id {}", child.getId());
        return child.getTasks().stream()
                .map(task -> NotificationDto.builder()
                        .localDateTime(task.getDeadline()).build())
                .toList();
    }

    @Override
    public List<TaskDto> getUserTaskById() {
        User child = getUser();
        log.debug("Request getUserTaskById by id {}", child.getId());
        return child.getTasks().stream()
                .map(task -> TaskDto.builder()
                        .taskStatus(task.getTaskStatus())
                        .title(task.getTask().getTitle())
                        .point(task.getRewardPoints())
                        .localDateTime(task.getDeadline())
                        .build())
                .toList();
    }


    private User getUser() {
        String uid = authorizationService.getUser().getUid();
        return userService.getUserByUid(uid);
    }


}
