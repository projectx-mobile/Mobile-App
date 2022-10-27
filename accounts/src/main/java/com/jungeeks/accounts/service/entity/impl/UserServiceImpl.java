package com.jungeeks.accounts.service.entity.impl;


import com.jungeeks.entity.User;
import com.jungeeks.accounts.exception.UserNotFoundException;
import com.jungeeks.accounts.repository.UserRepository;
import com.jungeeks.accounts.service.entity.UserService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("accountsUserService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    @Qualifier("accountsUserRepository")
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }

    @Override
    public User getUserByUid(String uId) {
        Optional<User> userByFirebaseId = userRepository.findUserByFirebaseId(uId);
        return userByFirebaseId.orElseThrow(() -> new UserNotFoundException(String.format("User with uid %s not found", uId)));
    }

    @Override
    public List<User> getAllByFamilyId(@NonNull String familyId) {
        return userRepository.findAllByFamilyId(familyId).orElseThrow(() -> new UserNotFoundException(String.format("Family id %s not found", familyId)));
    }

}
