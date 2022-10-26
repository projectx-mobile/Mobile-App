package com.jungeeks.populators;

import com.jungeeks.entity.Family;
import com.jungeeks.entity.Photo;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.accounts.repository.FamilyRepository;
import com.jungeeks.accounts.repository.UserRepository;
import com.jungeeks.service.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserPopulator {
    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void populate(){
        Family savedFamily = familyRepository.save(Family.builder()
                .id(new RandomString(9).nextString())
                .build());

        User parent = userRepository.save(
                User.builder()
                        .email("aaa@google.com")
                        .name("Parent")
                        .photo(List.of(Photo.builder()
                                        .path("photo.jpg")
                                        .creationDate(LocalDateTime.now())
                                .build()))
                        .points(0L)
                        .family(savedFamily)
                        .user_role(USER_ROLE.ADMIN)
                        .user_status(USER_STATUS.ACTIVE)
                        .build());
    }
}
