package com.jungeeks.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = RequestDtoChecksumServiceImplTest.class)
class RequestDtoChecksumServiceImplTest {

    @InjectMocks
    RequestDtoChecksumServiceImpl requestDtoChecksumService;

    private static final String token = "PGwLFCXcP4b6HjGOXQkaxv3tJwn2";
    private static final String checkSum = "3493";
    private static final String wrongCheckSum = "0";
    private static final String email = "kidsapptestacc@gmail.com";

    @Test
    void validatePositive() {
        boolean validate = requestDtoChecksumService.validate(token, email, checkSum);
        assertTrue(validate);
    }

    @Test
    void validateNegative() {
        boolean validate = requestDtoChecksumService.validate(token, email, wrongCheckSum);
        assertFalse(validate);
    }

    @Test
    void getChecksumPositive() {
        String newCheckSum = requestDtoChecksumService.getChecksum(token, email);
        assertEquals(newCheckSum, checkSum);
    }

    @Test
    void getChecksumNegative() {
        String newCheckSum = requestDtoChecksumService.getChecksum(token, email);
        assertNotEquals(newCheckSum, wrongCheckSum);
    }
}