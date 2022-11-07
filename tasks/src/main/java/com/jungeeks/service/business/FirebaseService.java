package com.jungeeks.service.business;

public interface FirebaseService {

    boolean sendMessage(String toClientAppId, String message, String toEmail, String fromEmail);
}
