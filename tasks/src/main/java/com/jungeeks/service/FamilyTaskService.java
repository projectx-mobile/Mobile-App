package com.jungeeks.service;

import com.jungeeks.dto.TaskDto;
import com.jungeeks.entity.FamilyTask;

import java.util.List;

public interface FamilyTaskService {

        /**
        * Gets all tasks.
        *
        * @return the all tasks
        */
        List<TaskDto> getAllTasks();

        /**
         * Gets all tasks by family id.
         *
         * @return the all tasks by family id
         */
        List<TaskDto> getAllTasksByUserId(Long userid);
        List<TaskDto> getAllTasksByCurrentUserUid();

}
