package com.jungeeks.controller;

import com.jungeeks.config.FirebaseConfigProperties;
import com.jungeeks.test.TestService;
import com.jungeeks.test.dto.SignInResponseDTO;
import com.jungeeks.test.dto.SignUpResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    FirebaseConfigProperties firebaseConfigProperties;

    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDTO> signUp(String email,String password){

        SignUpResponseDTO signUpResponseDTO = testService.signUp(email, password);

        return ResponseEntity.status(HttpStatus.OK).body(signUpResponseDTO);
    }

    @PostMapping("/sign_in")
    public ResponseEntity<SignInResponseDTO> signIn(String email,String password){

        SignInResponseDTO signInResponseDTO = testService.signIn(email, password);

        return ResponseEntity.status(HttpStatus.OK).body(signInResponseDTO);
    }

}
