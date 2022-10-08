package service.entity;

import com.jungeeks.entity.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    List<User> getAllByFamilyId(String familyId);

    User save(User user);
}
