package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.response.NotificationResponse;
import com.jungeeks.response.TaskResponse;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.entity.UserService;
import com.sun.istack.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link UserService}
 *
 * @author TorusTredent on 10.28.2022
 */
@Service("accounts_userServiceImpl")
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gets user task by id.
     *
     * @param childs the childs
     * @return the user task by id
     */
    @Override
    public List<TaskResponse> getUserTaskById(User childs) {
        log.debug("Request getUserTaskById by id {}", childs.getId());

        return childs.getTasks().stream()
                .map((child) ->
                        TaskResponse.builder()
                                .taskStatus(child.getTaskStatus())
                                .title(child.getTask().getTitle())
                                .point(child.getRewardPoints())
                                .localDateTime(child.getDeadline())
                                .build())
                .toList();
    }

    /**
     * Gets deadline of all task.
     *
     * @param child the child
     * @return the deadline of all task
     */
    @Override
    public List<NotificationResponse> getDeadlineOfAllTask(User child) {
        log.debug("Request getDeadlineOfAllTask by id {}", child.getId());

        return child.getTasks().stream()
                .map(task -> NotificationResponse.builder()
                        .localDateTime(task.getDeadline()).build())
                .toList();
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    @Override
    public User getUserById(Long id) {
        log.debug("Request getUserById by id {}", id);
        return userRepository.findUserById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }

    /**
     * Gets all by family id.
     *
     * @param familyId the family id
     * @return the all by family id
     */
    @Override
    public List<User> getAllByFamilyId(@NonNull String familyId) {
        log.debug("Request getAllByFamilyId by familyId {}", familyId);
        return userRepository.findAllByFamilyId(familyId).orElseThrow(
                () -> new UserNotFoundException(String.format("Family with id %s not found", familyId)));
    }

    /**
     * Gets all by family id and user role.
     *
     * @param familyId  the family id
     * @param user_role the user role
     * @return the all by family id and user role
     */
    @Override
    public List<User> getAllByFamilyIdAndUserRole(@NotNull String familyId, @NotNull USER_ROLE user_role) {
        log.debug("Request getAllByFamilyIdAndUserRole by familyId {} and userRole {}", familyId, user_role);
        return userRepository.findAllByFamilyIdAndUser_role(familyId, user_role).orElseThrow(
                () -> new UserNotFoundException(String.format("User with familyId %s and role %s not found",
                        familyId, user_role))
        );
    }

    /**
     * Gets user by firebase id.
     *
     * @param uid the uid
     * @return the user by firebase id
     */
    @Override
    public User getUserByFirebaseId(String uid) {
        log.debug("Request getUserByFirebaseId by uid {}", uid);
        return userRepository.findByFirebaseId(uid).orElseThrow(
                () -> new UserNotFoundException(String.format("User with uid %s not found", uid)));
    }
}
