package com.prateek.instagram.service;

import com.prateek.instagram.dto.CommentDto;
import com.prateek.instagram.exception.CommentDoesNotExistException;
import com.prateek.instagram.exception.PostDoesNotExistException;
import com.prateek.instagram.exception.UserDoesNotExistException;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllComment(Long postId) throws PostDoesNotExistException;
    List<CommentDto> getAllReply(Long commentId) throws CommentDoesNotExistException;
    CommentDto addComment(Long userId, Long postId, CommentDto comment) throws PostDoesNotExistException, UserDoesNotExistException;
    Object deleteComment(Long commentId);
    CommentDto updateComment(Long userId, Long postId, Long commentId, CommentDto commentDto) throws PostDoesNotExistException, UserDoesNotExistException, CommentDoesNotExistException;

}
