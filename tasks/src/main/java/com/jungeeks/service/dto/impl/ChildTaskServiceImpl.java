package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.SaveNewTaskDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildTaskServiceImpl implements ChildTaskService {

    private final TaskService taskService;
    private final CategoryService categoryService;
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final FamilyTaskService familyTaskService;

    @Autowired
    public ChildTaskServiceImpl(TaskService taskService,
                                CategoryService categoryService,
                                AuthorizationService authorizationService,
                                UserService userService,
                                FamilyTaskService familyTaskService) {
        this.taskService = taskService;
        this.categoryService = categoryService;
        this.authorizationService = authorizationService;
        this.userService = userService;
        this.familyTaskService = familyTaskService;
    }

    @Override
    public boolean saveTask(SaveNewTaskDto saveNewTaskDto) {
        User user = userService.getUserByUid(getUid());
        if (saveNewTaskDto.getTemplate() == null) {
            saveFamilyTaskWithNewTask(saveNewTaskDto, user);
        } else {
            saveFamilyTask(saveNewTaskDto, user);
        }
        return true;
    }

    private void saveFamilyTask(SaveNewTaskDto saveNewTaskDto, User user) {
        Task task = taskService.findByTemplate(saveNewTaskDto.getTemplate());
        FamilyTask familyTask = mapSaveNewTaskDtoToFamilyTask(saveNewTaskDto, user);
        familyTask.setTask(task);
        familyTaskService.save(familyTask);
    }

    private void saveFamilyTaskWithNewTask(SaveNewTaskDto saveNewTaskDto, User user) {
        FamilyTask familyTask = mapSaveNewTaskDtoToFamilyTask(saveNewTaskDto, user);
        Task taskDbTask = taskService.save(Task.builder()
                .taskType(TASK_TYPE.CUSTOM)
                .title(saveNewTaskDto.getTitle())
                .description(saveNewTaskDto.getDescription())
                .build());
        familyTask.setTask(taskDbTask);
        familyTaskService.save(familyTask);
    }

    private FamilyTask mapSaveNewTaskDtoToFamilyTask(SaveNewTaskDto saveNewTaskDto, User user) {
        return FamilyTask.builder()
                .creation(saveNewTaskDto.getCreation())
                .deadline(saveNewTaskDto.getDeadLine())
                .rewardPoints(saveNewTaskDto.getRewardPoints())
                .repeatable(saveNewTaskDto.isRepeatable())
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
