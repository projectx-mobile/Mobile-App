package com.jungeeks.service.dto.imp;

import com.jungeeks.dto.ChildDto;
import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.entitiy.FamilyTask;
import com.jungeeks.entitiy.RewardRequest;
import com.jungeeks.entitiy.User;
import com.jungeeks.entitiy.enums.REQUEST_STATUS;
import com.jungeeks.entitiy.enums.TASK_STATUS;
import com.jungeeks.entitiy.enums.USER_ROLE;
import com.jungeeks.service.dto.ParentService;
import com.jungeeks.service.entity.imp.UserServiceImp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParentServiceImp implements ParentService {

    private final UserServiceImp userServiceImp;

    /**
     * get data for the parent home page
     **/
    @Override
    public ParentHomeDto getParentHomeDate(Long id) {
        log.debug(String.format("Request getParentHomeDate by id %s", id));
        User userById = userServiceImp.getUserById(id);

        List<User> childs = userServiceImp.getAllByFamilyIdAndUserRole(userById.getFamily().getId(), USER_ROLE.CHILD);
        log.debug(String.format("Number of childs %s", childs.size()));

        List<ChildDto> childDtos = getChildDtoList(childs);

        return ParentHomeDto.builder()
                .familyId(userById.getFamily().getId())
                .childDtos(childDtos)
                .build();
    }

    /**
     * mapping list of Users to list of ChildDto
     **/

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

