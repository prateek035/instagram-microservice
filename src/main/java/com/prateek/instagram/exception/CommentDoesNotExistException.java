package com.prateek.instagram.exception;

public class CommentDoesNotExistException extends Exception{
    public CommentDoesNotExistException() {
        super("Comment does not exist.");
    }
}
