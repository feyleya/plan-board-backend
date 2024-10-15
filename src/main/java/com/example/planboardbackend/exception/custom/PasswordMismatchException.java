package com.example.planboardbackend.exception.custom;

public class PasswordMismatchException extends RuntimeException {
    private static final String MESSAGE = "Password and confirmation password do not match";

    public PasswordMismatchException() {
        super(MESSAGE);
    }
}
