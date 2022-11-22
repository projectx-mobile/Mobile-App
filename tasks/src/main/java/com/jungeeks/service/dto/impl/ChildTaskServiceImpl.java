package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.ChildNewTaskDto;
import com.jungeeks.dto.GetTaskDto;
import com.jungeeks.entity.ClientApp;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.Task;
import com.jungeeks.entity.User;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.jungeeks.entity.enums.TASK_STATUS.*;

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

    @Override
    public List<GetTaskDto> getTasksByDate(LocalDate date) {
        User user = userService.getUserByUid(getUid());
        log.debug("Find user with id {}", user.getId());

        LocalDateTime atStartOfDay = date.atStartOfDay();
        LocalDateTime nextStartOfDay = date.plusDays(1).atStartOfDay();

        List<FamilyTask> tasksByDate = familyTaskService.findAllByDate(atStartOfDay, nextStartOfDay);

        return mapFamilyTaskToGetTaskDto(tasksByDate);
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
                .taskStatus(PENDING)
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

    private List<GetTaskDto> mapFamilyTaskToGetTaskDto(List<FamilyTask> familyTasks) {
        log.debug("Mapping list familyTask to list getTaskDto");

        return familyTasks.stream()
                .filter(task -> !task.getTaskStatus().equals(REJECT) && !task.getTaskStatus().equals(NOT_ACTIVE))
                .map((task -> (GetTaskDto.builder()
                                        .id(task.getId())
                                        .title(task.getTask().getTitle())
                                        .penaltyPoints(task.getPenaltyPoints())
                                        .rewardPoints(task.getRewardPoints())
                                        .deadLine(task.getDeadline())
                                        .taskStatus(task.getTaskStatus())
                                        .build())))
                .toList();
    }
}
