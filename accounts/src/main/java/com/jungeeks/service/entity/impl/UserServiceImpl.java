package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.User;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.response.NotificationResponse;
import com.jungeeks.response.TaskResponse;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accounts_userServiceImpl")
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(String uid) {
        return userRepository.findByUid(uid).orElseThrow(
                () -> new UserNotFoundException(String.format("User %s not found", uid)));
    }

    public List<TaskResponse> getUserTaskById(User childs) {
        log.debug("Request getUserTaskById by id {}", childs.getId());

        return childs.getTasks().stream()
                .map((child) ->
                        TaskResponse.builder()
                                .taskStatus(child.getTaskStatus())
                                .title(child.getTask().getTitle())
                                .point(child.getRewardPoints())
                                .localDateTime(child.getDeadline())
                                .build())
                .toList();
    }

    public List<NotificationResponse> getDeadlineOfAllTask(User child) {
        log.debug("Request getDeadlineOfAllTask by id {}", child.getId());

        return child.getTasks().stream()
                .map(task -> NotificationResponse.builder()
                        .localDateTime(task.getDeadline()).build())
                .toList();
    }

}
