package com.jungeeks.exception;

public class PathNotFoundException extends RuntimeException{

    public PathNotFoundException(String message) {
        super(message);
    }
}
