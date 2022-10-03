package com.jungeeks.service.entity.imp;

import com.jungeeks.entitiy.User;
import com.jungeeks.entitiy.enums.USER_ROLE;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.entity.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }

    @Override
    public List<User> getAllByFamilyId(@NonNull String familyId) {
        return userRepository.findAllByFamilyId(familyId).orElseThrow(
                () -> new UserNotFoundException(String.format("Family id %s not found", familyId)));
    }

    @Override
    public List<User> getAllByFamilyIdAndUserRole(String familyId, USER_ROLE user_role) {
        return userRepository.findAllByFamilyIdAndUser_role(familyId, user_role).orElseThrow(
                () -> new UserNotFoundException(String.format("User with familyId %s and role %s not found",
                                                                                        familyId, user_role))
        );
    }

}
