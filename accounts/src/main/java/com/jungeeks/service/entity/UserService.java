package com.jungeeks.service.entity;

import com.jungeeks.entity.User;
import com.jungeeks.response.NotificationResponse;
import com.jungeeks.response.TaskResponse;

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


}
