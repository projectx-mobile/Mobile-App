package com.jungeeks.exception;

public class FamilyNotFoundException extends RuntimeException{

   public FamilyNotFoundException() {
    }

    public FamilyNotFoundException(String message) {
        super(message);
    }
}
