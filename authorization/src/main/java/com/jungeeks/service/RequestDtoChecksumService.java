package com.jungeeks.service;

public interface RequestDtoChecksumService {

    boolean validate(String  token, String email, String checksum);
    String getChecksum(String  token, String email);
}
