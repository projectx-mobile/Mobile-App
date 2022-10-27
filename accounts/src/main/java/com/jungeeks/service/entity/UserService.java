package com.jungeeks.service.entity;

import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    User getUserById(Long id);

    /**
     * Gets all by family id.
     *
     * @param familyId the family id
     * @return the all by family id
     */
    List<User> getAllByFamilyId(String familyId);

    /**
     * Gets all by family id and user role.
     *
     * @param familyId  the family id
     * @param user_role the user role
     * @return the all by family id and user role
     */
    List<User> getAllByFamilyIdAndUserRole(String familyId, USER_ROLE user_role);

    /**
     * Gets user by firebase id.
     *
     * @param uid the uid
     * @return the user by firebase id
     */
    User getUserByFirebaseId(String uid);
}
