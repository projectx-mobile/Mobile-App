package com.jungeeks.service.impl;

import com.jungeeks.service.RequestDtoChecksumService;
import org.springframework.stereotype.Service;

@Service
public class RequestDtoChecksumServiceImpl implements RequestDtoChecksumService {
    @Override
    public boolean validate(String token, String email, String checksum) {
        return getChecksum(token,email).equals(checksum);
    }

    @Override
    public String getChecksum(String token, String email) {
        long i = (long) (token.length() + email.length() * 145L - 15L);
        return Long.toString(i);
    }
}
