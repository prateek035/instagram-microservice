package com.prateek.instagram.exception;

public class PostDoesNotExistException extends Exception{
    public PostDoesNotExistException() {
        super("Post does not exist");
    }
}
