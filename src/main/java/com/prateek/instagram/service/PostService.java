package com.prateek.instagram.service;

import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.dto.PostDto;

import java.util.List;

public interface PostService {
    ResponseDto<List<PostDto>> getPosts(Long id);
    ResponseDto<Object> deleteUser(Long id);
    ResponseDto<PostDto> addPost(PostDto user);
    ResponseDto<PostDto> updatePost(PostDto user);
}
