package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.ChildNewTaskDto;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.Task;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.TASK_TYPE;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.dto.ChildTaskService;
import com.jungeeks.service.entity.CategoryService;
import com.jungeeks.service.entity.FamilyTaskService;
import com.jungeeks.service.entity.TaskService;
import com.jungeeks.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ChildTaskServiceImpl implements ChildTaskService {

    private final TaskService taskService;
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final FamilyTaskService familyTaskService;

    @Autowired
    public ChildTaskServiceImpl(TaskService taskService,
                                AuthorizationService authorizationService,
                                UserService userService,
                                FamilyTaskService familyTaskService) {
        this.taskService = taskService;
        this.authorizationService = authorizationService;
        this.userService = userService;
        this.familyTaskService = familyTaskService;
    }

    @Override
    public boolean saveTask(ChildNewTaskDto childNewTaskDto) {
        User user = userService.getUserByUid(getUid());
        log.debug("Find user with id {}", user.getId());

        saveFamilyTask(childNewTaskDto, user);
        return true;
    }

    private void saveFamilyTask(ChildNewTaskDto childNewTaskDto, User user) {
        FamilyTask familyTask = mapChildTaskDtoToFamilyTask(childNewTaskDto, user);
        Task task;
        log.debug("In ChildNewTaskDto template {}", childNewTaskDto.getTemplate());

        if (childNewTaskDto.getTemplate() != null) {
            task = taskService.findByTitle(childNewTaskDto.getTemplate());
        } else {
            task = taskService.save(Task.builder()
                    .taskType(TASK_TYPE.CUSTOM)
                    .title(childNewTaskDto.getTitle())
                    .description(childNewTaskDto.getDescription())
                    .build());
        }
        log.debug("Set task in familyTask with id {}", task.getId());
        familyTask.setTask(task);
        familyTaskService.save(familyTask);
    }

    private FamilyTask mapChildTaskDtoToFamilyTask(ChildNewTaskDto childNewTaskDto, User user) {
        return FamilyTask.builder()
                .creation(childNewTaskDto.getCreation())
                .deadline(childNewTaskDto.getDeadLine())
                .rewardPoints(childNewTaskDto.getRewardPoints())
                .repeatable(childNewTaskDto.isRepeatable())
                .users(List.of(user))
                .author(user)
                .family(user.getFamily())
                .taskStatus(TASK_STATUS.PENDING)
                .build();
    }

    private String getUid() {
        return authorizationService.getUser().getUid();
    }
}
