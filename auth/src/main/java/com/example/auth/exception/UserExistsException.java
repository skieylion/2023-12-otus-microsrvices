package com.example.auth.exception;

public class UserExistsException extends Exception {
    public UserExistsException() {
        super("This user is already registered");
    }
}
