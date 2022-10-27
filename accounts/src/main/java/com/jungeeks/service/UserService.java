package com.jungeeks.service;

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
     * @param id a unique child identifier
     * @return NotificationResponse list
     */
    List<NotificationResponse> getDeadlineOfAllTask(Long id);

    /**
     * Fetches a child account from database by child number.
     * Returns its task deadline if it has status ACCEPTED on its getting request,
     * otherwise - throws <code>EntityNotFoundException</code> with status NOT_FOUND
     *
     * @param id a unique account identifier
     * @return TaskResponse list
     */
    List<TaskResponse> getUserTaskById(Long id);

    User getUserById(Long id);


}
