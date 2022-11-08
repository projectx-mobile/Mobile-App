package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.ChildNewTaskDto;
import com.jungeeks.entity.ClientApp;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.Task;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.TASK_TYPE;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.business.FirebaseService;
import com.jungeeks.service.dto.ChildTaskService;
import com.jungeeks.service.entity.FamilyTaskService;
import com.jungeeks.service.entity.TaskService;
import com.jungeeks.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChildTaskServiceImpl implements ChildTaskService {

    private final TaskService taskService;
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final FamilyTaskService familyTaskService;
    private final FirebaseService firebaseService;

    /*
     * Add message
     */
    private static final String MESSAGE = "";

    @Autowired
    public ChildTaskServiceImpl(TaskService taskService,
                                AuthorizationService authorizationService,
                                UserService userService,
                                FamilyTaskService familyTaskService, FirebaseService firebaseService) {
        this.taskService = taskService;
        this.authorizationService = authorizationService;
        this.userService = userService;
        this.familyTaskService = familyTaskService;
        this.firebaseService = firebaseService;
    }

    @Override
    public boolean saveTask(ChildNewTaskDto childNewTaskDto) {
        User user = userService.getUserByUid(getUid());
        log.debug("Find user with id {}", user.getId());

        saveFamilyTask(childNewTaskDto, user);

        List<User> parents = userService.getAllByFamilyIdAndUserRoleWithAdmin(user.getFamily().getId(), USER_ROLE.PARENT);
        sendMessageForUsersWithEnableNotification(parents, user);

        return true;
    }


    private void saveFamilyTask(ChildNewTaskDto childNewTaskDto, User user) {
        FamilyTask familyTask = mapChildTaskDtoToFamilyTask(childNewTaskDto, user);
        Task task = new Task();
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
                .penaltyPoints(0L)
                .family(user.getFamily())
                .taskStatus(TASK_STATUS.PENDING)
                .build();
    }

    private void sendMessageForUsersWithEnableNotification(List<User> parents, User child) {
        List<String> clientAppIds = parents.stream()
                .filter(parent -> !parent.getParentNotifications().isAllOff() && parent.getParentNotifications().isNewRequest())
                .flatMap(parent -> parent.getClientApps().stream().map(ClientApp::getAppId))
                .collect(Collectors.toList());
        firebaseService.sendMessageForAll(clientAppIds, MESSAGE, child.getName());
    }

    private String getUid() {
        return authorizationService.getUser().getUid();
    }
}
