package com.prateek.instagram.controller;

import com.prateek.instagram.dto.PostDto;
import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.model.Post;
import com.prateek.instagram.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/{userId}")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public ResponseDto<PostDto> addPost(@RequestBody PostDto postDto,
                                        @PathVariable("userId") Long userId) {
        return postService.addPost(postDto,userId);
    }

    @PutMapping("/post")
    public ResponseDto<PostDto> updatePost(@RequestBody PostDto postDto,
                                           @PathVariable("userId") Long userId) {
        return postService.updatePost(postDto,userId);
    }

    @GetMapping("/post/{postId}")
    public ResponseDto<PostDto> getPost(@PathVariable("postId") Long postId) {
        return postService.getPost(postId);
    }

    @GetMapping("/posts")
    public ResponseDto<List<PostDto>> getAllPosts(@PathVariable("userId") Long userId) {
        return postService.getAllPosts(userId);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseDto<Object> deletePost(@PathVariable("postId") Long postId) {
        return postService.deletePost(postId);
    }

}
