package com.jungeeks.service.imp;

import com.jungeeks.dto.ChildDto;
import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.entitiy.FamilyTask;
import com.jungeeks.entitiy.RewardRequest;
import com.jungeeks.entitiy.User;
import com.jungeeks.entitiy.enums.REQUEST_STATUS;
import com.jungeeks.entitiy.enums.TASK_STATUS;
import com.jungeeks.entitiy.enums.USER_ROLE;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.ParentService;
import com.jungeeks.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ParentServiceImp implements UserService, ParentService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<User>> getAllByFamilyId(String familyId) {
        return Optional.empty();
    }

    @Override
    public ParentHomeDto getParentHomeDate(Long id) {
        log.debug(String.format("Request getParentHomeDate by id %s", id));
        User userById = userRepository.findUserById(id).orElseThrow(
                () -> new RuntimeException(String.format("User %s not found", id)));

        List<User> childs = userRepository.findAllByFamilyIdAndUser_role(
                userById.getFamily().getId(), USER_ROLE.CHILD).orElse(new ArrayList<>());

        List<ChildDto> childDtos = getChildDtoList(childs);

        return ParentHomeDto.builder()
                .familyId(userById.getFamily().getId())
                .childDtos(childDtos)
                .build();
    }


    private List<ChildDto> getChildDtoList(List<User> childs) {
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
                                    .filter(task -> Objects.equals(task.getAuthor().getId(), x.getId()))
                                    .map(FamilyTask::getId)
                                    .toList())
                            .build());
                }).toList();
    }

}

