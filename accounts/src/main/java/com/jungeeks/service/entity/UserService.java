package com.jungeeks.service.entity;

import com.jungeeks.entity.User;
import com.jungeeks.response.NotificationResponse;
import com.jungeeks.response.TaskResponse;
import com.jungeeks.entity.enums.USER_ROLE;

import java.util.List;
import java.util.Optional;


public interface UserService {

    /**
     * Fetches a child account from database by child number.
     * Returns its tasks if it has status ACCEPTED on its getting request,
     * otherwise - throws <code>UserNotFoundException</code> with status BAD_REQUEST
     *
     * @param user the user
     * @return NotificationResponse list
     */
    List<NotificationResponse> getDeadlineOfAllTask(User user);

    /**
     * Fetches a child account from database by child number.
     * Returns its task deadline if it has status ACCEPTED on its getting request,
     * otherwise - throws <code>EntityNotFoundException</code> with status NOT_FOUND
     *
     * @param user the user
     * @return TaskResponse list
     */
    List<TaskResponse> getUserTaskById(User user);

    User getUserById(String uid);

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
