package com.jungeeks.controller;

import com.jungeeks.dto.ParentHomeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jungeeks.service.imp.ParentServiceImp;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ParentServiceImp parentServiceImp;


    @GetMapping("/getParentHome")
    public ResponseEntity<ParentHomeDto> getParentHomePage() {
        return new ResponseEntity<>(parentServiceImp.getParentHomeDate(1L), HttpStatus.ACCEPTED);
    }
}
