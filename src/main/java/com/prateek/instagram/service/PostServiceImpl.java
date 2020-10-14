package com.prateek.instagram.service;

import com.prateek.instagram.dto.PostDto;
import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{


    @Override
    public ResponseDto<List<PostDto>> getPosts(Long id) {
        return null;
    }

    @Override
    public ResponseDto<Object> deleteUser(Long id) {
        return null;
    }

    @Override
    public ResponseDto<PostDto> addPost(PostDto user) {
        return null;
    }

    @Override
    public ResponseDto<PostDto> updatePost(PostDto user) {
        return null;
    }
}
