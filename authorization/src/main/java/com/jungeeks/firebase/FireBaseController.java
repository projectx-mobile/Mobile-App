package com.jungeeks.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.jungeeks.email.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FireBaseController {

    @Autowired
    UserService userService;

    @GetMapping("/api/user/get")
    public ResponseEntity<User> get() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String uid = authentication.getName();
        User user = userService.get(uid);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("api/user/save")
    public ResponseEntity<User> save() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String uid = authentication.getName();
        UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);

        User user = new User();
        user.setEmail(userRecord.getEmail());
        user.setName(userRecord.getDisplayName());
        user.setUid(uid);
        user.setPhotoUrl(userRecord.getPhotoUrl());

        User savedUser = userService.save(user);

        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }
}
