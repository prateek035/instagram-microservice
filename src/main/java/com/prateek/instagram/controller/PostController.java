package com.prateek.instagram.controller;

import com.prateek.instagram.dto.PostDto;
import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.exception.UserDoesNotExistException;
import com.prateek.instagram.service.PostService;
import com.prateek.instagram.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user/{userId}")
public class PostController {

    @Autowired
    private  PostService postService;


    @PostMapping("/post")
    public ResponseDto<PostDto> addPost(@RequestBody PostDto postDto,
                                        @PathVariable("userId") Long userId) {
        try{
            return MapperUtil.convertToResponseDto(200,
                    "Post added success.",
                    postService.addPost(postDto,userId));

        } catch (UserDoesNotExistException e) {
            return MapperUtil.convertToResponseDto(404,
                    e.getMessage(),
                    null);
        }

    }

    @PutMapping("/post")
    public ResponseDto<PostDto> updatePost(@RequestBody PostDto postDto,
                                           @PathVariable("userId") Long userId) {
        try{
            return MapperUtil.convertToResponseDto(200,
                    "Post updated",
                    postService.updatePost(postDto,userId));

        } catch (UserDoesNotExistException e) {
            return MapperUtil.convertToResponseDto(404,
                    e.getMessage(),
                    null);
        }


    }

    @GetMapping("/post/{postId}")
    public ResponseDto<PostDto> getPost(@PathVariable("postId") Long postId) {

        Optional<PostDto> optionalPost = postService.getPost(postId);

        if(optionalPost.isPresent()) {
            return MapperUtil.convertToResponseDto(200,
                    "Post Found!",
                    optionalPost.get());
        }else {
            return MapperUtil.convertToResponseDto(404,
                    "Post Not Found!",
                    null);
        }

    }

    @GetMapping("/posts")
    public ResponseDto<List<PostDto>> getAllPosts(@PathVariable("userId") Long userId) {
        return MapperUtil.convertToResponseDto(200,
                                "Post found success!",
                                postService.getAllPosts(userId));

    }

    @DeleteMapping("/post/{postId}")
    public ResponseDto<String> deletePost(@PathVariable("postId") Long postId) {

        return MapperUtil.convertToResponseDto(200,
                "Post Deleted!",
                postService.deletePost(postId));
    }

}
