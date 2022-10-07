package service.entity.imp;

import com.jungeeks.entitiy.User;
import com.sun.istack.NotNull;
import exception.UserNotFoundException;
import io.swagger.annotations.Authorization;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import service.entity.UserService;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }

    @Override
    public List<User> getAllByFamilyId(@NotNull String familyId) {
        return userRepository.findAllByFamilyId(familyId).orElseThrow(() -> new UserNotFoundException(String.format("Family id %s not found", familyId)));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
