package com.jungeeks.controllers;

import com.jungeeks.entitiy.User;
import com.jungeeks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PersonalInfController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public User getUserById(Long id){
        Optional<User> userById = userRepository.findUserById(id);

        return userById.orElseGet(User::new);
    }

}
