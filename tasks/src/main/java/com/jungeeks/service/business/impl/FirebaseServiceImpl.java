package com.jungeeks.service.business.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.jungeeks.service.business.FirebaseService;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public boolean sendMessage(String toClientAppId, String message, String toEmail, String fromName) {
        Message mes = Message.builder()
                .putData("name", fromName)
                .putData("request", message)
                .setToken(toClientAppId)
                .build();
        try {
            FirebaseMessaging.getInstance().send(mes);
            log.debug(String.format("Notification sent to %s", toEmail));

            return true;
        } catch (FirebaseMessagingException fme) {
            log.error("Notification not sent:" + fme.getLocalizedMessage() +
                    " code:" + fme.getMessagingErrorCode());
        }
        return false;
    }
}
