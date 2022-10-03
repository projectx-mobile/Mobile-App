package com.jungeeks.service.entity.imp;

import com.jungeeks.entitiy.User;
import com.jungeeks.entitiy.enums.USER_ROLE;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.entity.UserService;
import com.sun.istack.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        log.debug(String.format("Request getUserById by id %s", id));
        return userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }

    @Override
    public List<User> getAllByFamilyId(@NonNull String familyId) {
        log.debug(String.format("Request getAllByFamilyId by familyId %s", familyId));
        return userRepository.findAllByFamilyId(familyId).orElseThrow(
                () -> new UserNotFoundException(String.format("Family with id %s not found", familyId)));
    }

    @Override
    public List<User> getAllByFamilyIdAndUserRole(@NotNull String familyId, @NotNull USER_ROLE user_role) {
        log.debug(String.format("Request getAllByFamilyIdAndUserRole by familyId %s and userRole %s", familyId, user_role));
        return userRepository.findAllByFamilyIdAndUser_role(familyId, user_role).orElseThrow(
                () -> new UserNotFoundException(String.format("User with familyId %s and role %s not found",
                                                                                        familyId, user_role))
        );
    }

}
