package com.jungeeks.service;

import com.jungeeks.dto.VerifyRequestDto;

public interface EmailService {

    boolean send(VerifyRequestDto verifyRequestDto);
}
