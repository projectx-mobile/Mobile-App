package com.jungeeks.service;

import com.jungeeks.dto.VerifyRequestDto;

public interface EmailService {
    public void send(VerifyRequestDto verifyRequestDto);

}
