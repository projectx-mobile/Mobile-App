package service.entity;

import com.jungeeks.entitiy.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    List<User> getAllByFamilyId(String familyId);

    User save(User user);
}
