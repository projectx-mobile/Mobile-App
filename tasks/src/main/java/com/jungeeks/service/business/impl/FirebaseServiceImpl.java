package com.jungeeks.service.business.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.jungeeks.service.business.FirebaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public boolean sendMessage(String toClientAppId, String message, String toEmail, String fromName) {
        Message mes = Message.builder()
                .putData("name", fromName)
                .putData("message", message)
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

    @Override
    public boolean sendMessageForAll(List<String> clientApp, String message, String fromName) {
        MulticastMessage mes = MulticastMessage.builder()
                .addAllTokens(clientApp)
                .putData("name", fromName)
                .putData("message", message)
                .build();
        try {
            FirebaseMessaging.getInstance().sendMulticast(mes);
            log.debug(String.format("Notification sent to %s", clientApp));

            return true;
        } catch (FirebaseMessagingException fme) {
            log.error("Notification not sent:" + fme.getLocalizedMessage() +
                    " code:" + fme.getMessagingErrorCode());
        }
        return false;
    }
}
