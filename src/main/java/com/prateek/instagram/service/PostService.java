package com.prateek.instagram.service;

import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.dto.PostDto;
import com.prateek.instagram.exception.UserDoesNotExistException;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<PostDto> getPost(Long id);
    List<PostDto> getAllPosts(Long userId);
    String deletePost(Long id);
    PostDto addPost(PostDto user, Long userId) throws UserDoesNotExistException;
    PostDto updatePost(PostDto postDto, Long userId) throws UserDoesNotExistException;
}
