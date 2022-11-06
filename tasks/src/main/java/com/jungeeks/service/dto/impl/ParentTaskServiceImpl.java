package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.ParentNewTaskDto;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.Task;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.TASK_TYPE;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.dto.ParentTaskService;
import com.jungeeks.service.entity.CategoryService;
import com.jungeeks.service.entity.FamilyTaskService;
import com.jungeeks.service.entity.TaskService;
import com.jungeeks.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentTaskServiceImpl implements ParentTaskService {

    private final TaskService taskService;
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final FamilyTaskService familyTaskService;

    @Autowired
    public ParentTaskServiceImpl(TaskService taskService,
                                 AuthorizationService authorizationService,
                                 UserService userService,
                                 FamilyTaskService familyTaskService) {
        this.taskService = taskService;
        this.authorizationService = authorizationService;
        this.userService = userService;
        this.familyTaskService = familyTaskService;
    }

    @Override
    public boolean saveTask(ParentNewTaskDto parentNewTaskDto) {
        User user = userService.getUserByUid(getUid());
        saveFamilyTask(parentNewTaskDto, user);
        return true;
    }

    private void saveFamilyTask(ParentNewTaskDto parentNewTaskDto, User user) {
        List<User> childs = parentNewTaskDto.getUserIds().stream().map(userService::getUserById).toList();
        FamilyTask familyTask = mapParentTaskDtoToFamilyTask(parentNewTaskDto, user, childs);
        Task task;
        if (parentNewTaskDto.getTemplate() != null) {
            task = taskService.findByTitle(parentNewTaskDto.getTemplate());
        } else {
            task = taskService.save(Task.builder()
                    .taskType(TASK_TYPE.CUSTOM)
                    .title(parentNewTaskDto.getTitle())
                    .description(parentNewTaskDto.getDescription())
                    .build());
        }
        familyTask.setTask(task);
        familyTaskService.save(familyTask);
    }

    private FamilyTask mapParentTaskDtoToFamilyTask(ParentNewTaskDto parentNewTaskDto, User user, List<User> childs) {
        return FamilyTask.builder()
                .creation(parentNewTaskDto.getCreation())
                .deadline(parentNewTaskDto.getDeadLine())
                .rewardPoints(parentNewTaskDto.getRewardPoints())
                .penaltyPoints(parentNewTaskDto.getPenaltyPoints())
                .repeatable(parentNewTaskDto.isRepeatable())
                .photoReport(parentNewTaskDto.isPhotoReport())
                .users(childs)
                .author(user)
                .family(user.getFamily())
                .taskStatus(TASK_STATUS.ACTIVE)
                .build();
    }

    private String getUid() {
        return authorizationService.getUser().getUid();
    }
}
