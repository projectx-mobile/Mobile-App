package com.jungeeks.services.impl;

import com.jungeeks.entitiy.enums.TASK_STATUS;
import com.jungeeks.exceptionhandler.UserNotFoundException;
import com.jungeeks.response.TaskResponse;
import com.jungeeks.entitiy.User;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<TaskResponse> getUserTaskById(Long id) {
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
    public List<TaskResponse> getUserActiveTaskById(Long id) {
        User childs = userRepository.findUserById(id).orElse(null);

        return childs.getTasks().stream()
                .filter(task -> task.getTaskStatus().equals(TASK_STATUS.ACTIVE))
                .map((child) ->TaskResponse.builder()
                        .taskStatus(child.getTaskStatus())
                        .title(child.getTask().getTitle())
                        .point(childs.getPoints()).build()
        ).toList();
    }

}
