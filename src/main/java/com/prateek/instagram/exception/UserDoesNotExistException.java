package com.prateek.instagram.exception;

public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String err) {
        super(err);
    }
}
