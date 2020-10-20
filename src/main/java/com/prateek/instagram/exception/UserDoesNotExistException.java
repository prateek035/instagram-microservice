package com.prateek.instagram.exception;

public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException() {
        super("User Does not exist");
    }

    public UserDoesNotExistException(String err) {super(err);}
}
