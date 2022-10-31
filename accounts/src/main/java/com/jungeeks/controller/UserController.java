package com.jungeeks.controller;

import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.entity.User;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.dto.ParentService;
import com.jungeeks.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jungeeks.service.dto.imp.ParentServiceImp;

/**
 * Rest controller for operations related to parent home page:
 * operation getting data for home page
 *
 * @author TorusTredent 10.28.2022
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private ParentService parentService;
    private UserService userService;
    private AuthorizationService authorizationService;

    /**
     * Instantiates a new User controller.
     *
     * @param parentService        the parent service
     * @param userService          the user service
     * @param authorizationService the authorization service
     */
    @Autowired
    public UserController(@Qualifier("accounts_parentServiceImpl") ParentService parentService,
                          @Qualifier("accounts_userServiceImpl") UserService userService,
                          @Qualifier("utils_authorizationServiceImpl") AuthorizationService authorizationService) {
        this.parentService = parentService;
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    /**
     * Gets data for parent home page.
     *
     * @return data for parent home page
     */
    @GetMapping("/getParentHome")
    public ResponseEntity<ParentHomeDto> getDataForParentHomePage() {
        SecurityUserFirebase user = authorizationService.getUser();
        User userDb = userService.getUserByFirebaseId(user.getUid());
        log.debug("Fetching data for parent home page with familyId {}", userDb.getFamily());
        return new ResponseEntity<>(parentService.getParentHomeDate(userDb), HttpStatus.OK);
    }
}
