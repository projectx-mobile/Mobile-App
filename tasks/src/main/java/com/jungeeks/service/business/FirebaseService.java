package com.jungeeks.service.business;

import java.util.List;

public interface FirebaseService {

    boolean sendMessage(String toClientAppId, String message, String toEmail, String fromEmail);

    boolean sendMessageForAll(List<String> clientApp, String message, String fromName);
}
