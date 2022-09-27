package com.jungeeks.services.impl;

import com.jungeeks.entitiy.FamilyTask;
import com.jungeeks.entitiy.Task;
import com.jungeeks.entitiy.User;
import com.jungeeks.entitiy.enums.TASK_STATUS;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<FamilyTask> getUserActiveTaskById(Long id) {
        User child = userRepository.findUserById(id).orElse(null);
        System.out.println(child.toString());
        System.out.println(child.getTasks());
        List<FamilyTask> listOfActiveTasks =  child.getTasks().stream().filter(task -> task.equals(TASK_STATUS.ACTIVE)).collect(Collectors.toList());
        return listOfActiveTasks;
    }

    public List<FamilyTask> getUserTaskById(Long id) {
        User child = userRepository.findUserById(id).orElse(null);
        List<FamilyTask> listOfTasks =  child.getTasks().stream().collect(Collectors.toList());
        return listOfTasks;
    }

    public Optional<List<User>> getAllByFamilyId(String familyId) {
        return userRepository.findAllByFamilyId(familyId);
    }

}
