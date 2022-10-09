package com.jungeeks.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.jungeeks.dto.VerifyRequestDto;
import com.jungeeks.service.EmailService;
import com.jungeeks.service.RequestDtoChecksumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/registration/email")
@Slf4j
public class EmailRegistrationController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private RequestDtoChecksumService requestDtoChecksumService;

    @ResponseBody
    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerifyRequestDto verifyRequestDto) {

        emailService.send(verifyRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(value = "/verify", params = {"registration_token", "checksum", "email"})
    public ModelAndView verifyLink(@RequestParam(name = "registration_token") String token,
                                   @RequestParam(name = "checksum") String checksum,
                                   @RequestParam(name = "email") String email) {

        boolean validate = requestDtoChecksumService.validate(token, email, checksum);

        if (validate) {
            Message message = Message.builder()
                    .putData("email", email)
                    .putData("verify", "true")
                    .setToken(token)
                    .build();

            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException fme) {
                log.error("Notification not sent:" + fme.getLocalizedMessage() +
                        " code:" + fme.getMessagingErrorCode());
            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("email", email);
        model.put("validate", validate);
        return new ModelAndView("emailVerify", model);
    }

}
