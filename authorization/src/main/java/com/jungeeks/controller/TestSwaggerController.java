package com.jungeeks.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger")
public class TestSwaggerController {
    @GetMapping
    public ResponseEntity<String> swaggerEndpoint(){
        return ResponseEntity.ok("this is swagger");
    }
}
