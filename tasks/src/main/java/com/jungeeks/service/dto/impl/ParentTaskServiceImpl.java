package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.ConfirmTaskDto;
import com.jungeeks.dto.ParentNewTaskDto;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.Task;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.TASK_TYPE;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.exception.enums.ERROR_CODE;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.business.FirebaseService;
import com.jungeeks.service.dto.ParentTaskService;
import com.jungeeks.service.entity.FamilyTaskService;
import com.jungeeks.service.entity.TaskService;
import com.jungeeks.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ParentTaskServiceImpl implements ParentTaskService {

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
    public ParentTaskServiceImpl(TaskService taskService,
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
    public boolean saveTask(ParentNewTaskDto parentNewTaskDto) {
        User user = userService.getUserByUid(getUid());
        log.debug("Find user with id {}", user.getId());

        saveFamilyTask(parentNewTaskDto, user);

        List<User> childs = userService.getAllByFamilyIdAndUserRole(user.getFamily().getId(), USER_ROLE.CHILD);
        sendMessageForUsersWithEnableNewTaskNotification(childs, user);
        return true;
    }

    @Override
    public boolean confirmTask(ConfirmTaskDto confirmTaskDto) {
        User user = userService.getUserByUid(getUid());
        log.debug("Find user with id {}", user.getId());

        FamilyTask familyTask = familyTaskService.findById(confirmTaskDto.getTaskId());
        log.debug("FamilyTask with familyId {} and user with familyId {}", familyTask.getFamily().getId(), user.getFamily().getId());

        if (familyTask.getFamily().equals(user.getFamily()) && familyTask.getTaskStatus() == TASK_STATUS.PENDING) {
            familyTask.setPhotoReport(confirmTaskDto.isPhotoReport());
            familyTask.setRewardPoints(confirmTaskDto.getRewardPoints());
            familyTask.setTaskStatus(TASK_STATUS.ACTIVE);
            FamilyTask familyTaskDb = familyTaskService.save(familyTask);
            log.debug("Confirm familyTask with id {}", familyTask.getId());

            sendMessageForUsersWithEnableConfirmNotification(familyTaskDb.getAuthor(), user);
        } else {
            throw new BusinessException(String.format("Family id %s or task status %s is bad", familyTask.getFamily().getId(),
                                            familyTask.getTaskStatus()), ERROR_CODE.FAMILY_TASK_DONT_MATCH);
        }
        return true;
    }

    @Override
    public boolean rejectTask(Long taskId) {
        User user = userService.getUserByUid(getUid());
        log.debug("Find user with id {}", user.getId());

        FamilyTask familyTask = familyTaskService.findById(taskId);
        log.debug("FamilyTask with familyId {} and user with familyId {}", familyTask.getFamily().getId(), user.getFamily().getId());

        if (user.getFamily() == familyTask.getFamily() && familyTask.getTaskStatus() == TASK_STATUS.PENDING) {
            familyTask.setTaskStatus(TASK_STATUS.REJECT);
            familyTaskService.save(familyTask);
            log.debug("Reject familyTask with id {}", familyTask.getId());

        } else {
            throw new BusinessException(String.format("Family id %s or task status %s is bad", familyTask.getFamily().getId(),
                                            familyTask.getTaskStatus()), ERROR_CODE.FAMILY_TASK_DONT_MATCH);
        }
        return true;
    }

    private void saveFamilyTask(ParentNewTaskDto parentNewTaskDto, User user) {
        List<User> childs = parentNewTaskDto.getUserIds().stream()
                                                          .map(userService::getUserById)
                                                          .toList();
        log.debug("Size of child list {}", childs.size());

        FamilyTask familyTask = mapParentTaskDtoToFamilyTask(parentNewTaskDto, user, childs);
        Task task;
        log.debug("In ParentNewTaskDto template {}", parentNewTaskDto.getTemplate());

        if (parentNewTaskDto.getTemplate() != null) {
            task = taskService.findByTitle(parentNewTaskDto.getTemplate());
        } else {
            task = taskService.save(Task.builder()
                    .taskType(TASK_TYPE.CUSTOM)
                    .title(parentNewTaskDto.getTitle())
                    .description(parentNewTaskDto.getDescription())
                    .build());
        }
        log.debug("Find task with id {}", task.getId());

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

    private void sendMessageForUsersWithEnableConfirmNotification(User child, User parent) {
        List<String> clientAppIds = new ArrayList<>();
        if (!child.getChildNotifications().isAllOff() && child.getChildNotifications().isConfirmTask()) {
            child.getClientApps()
                    .forEach(clientApp -> clientAppIds.add(clientApp.getAppId()));
        }
        firebaseService.sendMessageForAll(clientAppIds, MESSAGE, parent.getName());
    }

    private void sendMessageForUsersWithEnableNewTaskNotification(List<User> childs, User parent) {
        List<String> clientAppIds = new ArrayList<>();
        childs.stream()
                .filter(child -> !child.getChildNotifications().isAllOff() && child.getChildNotifications().isNewTask())
                .forEach(user -> {
                            user.getClientApps()
                                    .forEach(clientApp -> {
                                        clientAppIds.add(clientApp.getAppId());
                                    });
                });
        firebaseService.sendMessageForAll(clientAppIds, MESSAGE, parent.getName());
    }

    private String getUid() {
        return authorizationService.getUser().getUid();
    }
}
