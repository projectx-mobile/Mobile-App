package com.jungeeks.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger")
public class TestSwaggerController {
    @GetMapping
    public ResponseEntity<String> test(){

        return ResponseEntity.ok("OK");
    }
    @GetMapping("tst")
    public ResponseEntity<String> test1(){

        return ResponseEntity.ok("OK");
    }

}
