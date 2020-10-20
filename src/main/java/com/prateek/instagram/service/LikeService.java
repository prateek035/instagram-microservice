package com.prateek.instagram.service;

import com.prateek.instagram.dto.LikeDto;
import com.prateek.instagram.exception.LikeDoesNotExistException;
import com.prateek.instagram.exception.PostDoesNotExistException;

import java.util.List;

public interface LikeService {
    List<LikeDto> getAllLikes(Long postId);
    String dislikePost(Long userId, Long postId) throws PostDoesNotExistException, LikeDoesNotExistException;
    LikeDto likePost(Long userId, Long postId) throws Exception;
}
