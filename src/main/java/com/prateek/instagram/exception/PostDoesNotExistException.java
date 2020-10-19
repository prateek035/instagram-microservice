package com.prateek.instagram.exception;

public class PostDoesNotExistException extends Exception{
    public PostDoesNotExistException(String err) {
        super(err);
    }
}
