package com.jungeeks.service.dto.imp;

import com.jungeeks.dto.ChildDto;
import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.RewardRequest;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.REQUEST_STATUS;
import com.jungeeks.entity.enums.TASK_STATUS;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.service.dto.ParentService;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.service.entity.imp.UserServiceImp;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link ParentService}
 *
 * @author TorusTredent on 10.28.2022
 */
@Service("account_parentServiceImpl")
@Slf4j
public class ParentServiceImp implements ParentService {

    @Autowired
    private UserServiceImp userServiceImp;

    /**
     * get data for the parent home page
     *
     * @param user the user
     * @return the parent home date
     */
    @Override
    public ParentHomeDto getParentHomeDate(User user) {
        log.debug("Request getParentHomeDate by user with uid {}", user.getFirebaseId());
        List<User> childs = userServiceImp.getAllByFamilyIdAndUserRole(user.getFamily().getId(), USER_ROLE.CHILD);

        log.debug("Number of childs {}", childs.size());
        List<ChildDto> childDtos = getChildDtoList(childs);
        return ParentHomeDto.builder()
                .familyId(user.getFamily().getId())
                .childDtos(childDtos)
                .build();
    }


    private List<ChildDto> getChildDtoList(List<User> childs) {
        log.debug("Mapping list child to list childDto");
        return childs.stream()
                .map((x) -> {
                    String[] split = x.getPhoto().size() == 0 ? null : x.getPhoto().get(0).getPath().split("/");
                    return (ChildDto.builder()
                            .name(x.getName())
                            .photoFileName(split == null ? null : split[split.length - 1])
                            .numberOfCompletedTasks(x.getTasks().stream()
                                    .filter(familyTask -> familyTask.getAuthor().getId().equals(x.getId())
                                            && familyTask.getTaskStatus() == TASK_STATUS.COMPLETED)
                                    .count())
                            .numberOfActiveTasks(x.getTasks().stream()
                                    .filter(familyTask -> familyTask.getAuthor().getId().equals(x.getId())
                                            && familyTask.getTaskStatus() == TASK_STATUS.ACTIVE)
                                    .count())
                            .rewardRequestIdsDtos(x.getFamily().getRequests().stream()
                                    .filter(request -> request.getUser().getId().equals(x.getId())
                                            && request.getRequestStatus() == REQUEST_STATUS.PENDING)
                                    .map(RewardRequest::getId)
                                    .toList())
                            .taskRequestIdsDtos(x.getTasks().stream()
                                    .filter(task -> Objects.equals(task.getAuthor().getId(), x.getId())
                                            && task.getTaskStatus() == TASK_STATUS.PENDING)
                                    .map(FamilyTask::getId)
                                    .toList())
                            .build());
                }).toList();
    }

}

