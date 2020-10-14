package com.prateek.instagram.service;

import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.dto.PostDto;

import java.util.List;
import java.util.Optional;

public interface PostService {

    ResponseDto<PostDto> getPost(Long id);
    ResponseDto<List<PostDto>> getAllPosts(Long userId);
    ResponseDto<Object> deletePost(Long id);
    ResponseDto<PostDto> addPost(PostDto user, Long userId);
    ResponseDto<PostDto> updatePost(PostDto postDto, Long userId);
}
