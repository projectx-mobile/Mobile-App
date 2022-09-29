package com.jungeeks.service.impl;

import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.response.NotificationResponse;
import com.jungeeks.response.TaskResponse;
import com.jungeeks.entitiy.User;
import com.jungeeks.repository.UserRepository;
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
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<TaskResponse> getUserTaskById(Long id) {
        log.debug(String.format("Request getUserTaskById by id %s", id));
        User childs = userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User %s not found", id)));

        return childs.getTasks().stream()
                .map((child) ->
                        TaskResponse.builder()
                                .taskStatus(child.getTaskStatus())
                                .title(child.getTask().getTitle())
                                .point(child.getPoints())
                                .localDateTime(child.getDeadline())
                                .build()
                ).toList();
    }

    public List<NotificationResponse> getDeadlineOfAllTask(Long id) {
        log.debug(String.format("Request getDeadlineOfAllTask by id %s", id));
        User child = userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User %s not found", id)));

        return child.getTasks().stream()
                .map(task -> NotificationResponse.builder()
                        .localDateTime(task.getDeadline()).build()
                ).toList();

    }

}
