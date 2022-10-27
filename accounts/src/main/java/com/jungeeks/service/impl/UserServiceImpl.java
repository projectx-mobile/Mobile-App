package com.jungeeks.service.impl;

import com.jungeeks.entity.User;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.response.NotificationResponse;
import com.jungeeks.response.TaskResponse;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User %s not found", id)));
    }

    public List<TaskResponse> getUserTaskById(Long id) {
        log.debug(String.format("Request getUserTaskById by id %s", id));
        String uid = authorizationService.getUser().getUid();
        User childs = userRepository.findByUid(uid);

        return childs.getTasks().stream()
                .map((child) ->
                        TaskResponse.builder()
                                .taskStatus(child.getTaskStatus())
                                .title(child.getTask().getTitle())
                                .point(child.getRewardPoints())
                                .localDateTime(child.getDeadline())
                                .build()
                ).toList();
    }

    public List<NotificationResponse> getDeadlineOfAllTask(Long id) {
        log.debug(String.format("Request getDeadlineOfAllTask by id %s", id));
        User child = getUserById(id);

        return child.getTasks().stream()
                .map(task -> NotificationResponse.builder()
                        .localDateTime(task.getDeadline()).build()
                ).toList();

    }

}
