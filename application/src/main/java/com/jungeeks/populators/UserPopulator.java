//package com.jungeeks.populators;
//
//import com.jungeeks.entitiy.Family;
//import com.jungeeks.entitiy.Photo;
//import com.jungeeks.entitiy.SocialCredentials;
//import com.jungeeks.entitiy.User;
//import com.jungeeks.entitiy.enums.USER_ROLE;
//import com.jungeeks.entitiy.enums.USER_STATUS;
//import com.jungeeks.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Component
//public class UserPopulator {
//    @Autowired
//    private FamilyRepository familyRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostConstruct
//    public void populate(){
//        Family savedFamily = familyRepository.save(Family.builder()
//                .id(new RandomString(9).nextString())
//                .build());
//
//        User parent = userRepository.save(
//                User.builder()
//                        .email("aaa@google.com")
//                        .name("Parent")
//                        .photo(List.of(Photo.builder()
//                                        .path("photo.jpg")
//                                        .creationDate(LocalDateTime.now())
//                                .build()))
//                        .family(savedFamily)
//                        .user_role(USER_ROLE.ADMIN)
//                        .user_status(USER_STATUS.ACTIVE)
//                        .socialCredentials(SocialCredentials.builder().build())
//                        .build());
//    }
//}
