package com.jungeeks.service.business.impl;

import com.jungeeks.dto.NotificationDto;
import com.jungeeks.dto.TaskDto;
import com.jungeeks.entity.User;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.business.ChildService;
import com.jungeeks.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ChildServiceImpl implements ChildService {
    private AuthorizationService authorizationService;

    private UserService userService;

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Override
    public List<NotificationDto> getDeadlineOfAllTask() {
        String uid = authorizationService.getUser().getUid();
        User user =  userService.getUserByUid(uid);
        return
    }

    @Override
    public List<TaskDto> getUserTaskById() {
        return null;
    }
}
