package com.jungeeks.services;

import com.jungeeks.response.TaskResponse;
import com.jungeeks.entitiy.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<TaskResponse> getUserActiveTaskById(Long id);

    List<TaskResponse> getUserTaskById(Long id);


}
