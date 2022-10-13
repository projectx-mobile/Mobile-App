package com.jungeeks.dto.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.jungeeks.entity.SecurityUserFirebase;
import com.jungeeks.service.SecurityService;
import com.jungeeks.test.TestService;
import com.jungeeks.test.dto.SignInResponseDTO;
import com.jungeeks.test.dto.SignUpResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private SecurityService securityService;



    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDTO> signUp(String email,String password) throws FirebaseAuthException {
        SignUpResponseDTO signUpResponseDTO = testService.signUp(email, password);
        return ResponseEntity.status(HttpStatus.OK).body(signUpResponseDTO);
    }

    @PostMapping("/sign_in")
    public ResponseEntity<SignInResponseDTO> signIn(String email,String password){
            SecurityUserFirebase user = securityService.getUser();
        SignInResponseDTO signInResponseDTO = testService.signIn(email, password);

        return ResponseEntity.status(HttpStatus.OK).body(signInResponseDTO);
    }
}
