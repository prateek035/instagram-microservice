package com.prateek.instagram.controller;

import com.prateek.instagram.dto.LikeDto;
import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.exception.LikeDoesNotExistException;
import com.prateek.instagram.exception.PostDoesNotExistException;
import com.prateek.instagram.service.LikeService;
import com.prateek.instagram.util.MapperUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/{userId}/post/{postId}")
public class LikeController {


    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/like")
    public ResponseDto<LikeDto> likePost(@PathVariable("userId") Long userId,
                                         @PathVariable("postId") Long postId) throws Exception {

        return MapperUtil.convertToResponseDto(200,
                                            "You liked a post!",
                                            likeService.likePost(userId, postId)
        );
    }

    @DeleteMapping("/like")
    public ResponseDto<Object> dislikePost(@PathVariable("userId") Long userId,
                                           @PathVariable("postId") Long postId) throws PostDoesNotExistException, LikeDoesNotExistException {

        return MapperUtil.convertToResponseDto(200,
                                            "You disliked a post!",
                                            likeService.dislikePost(userId, postId)
        );
    }

    @GetMapping("/likes")
    public ResponseDto<List<LikeDto>> getAllLikes(@PathVariable("postId") Long postId) {
        return MapperUtil.convertToResponseDto(200,
                                            "List of Users who liked the post",
                                            likeService.getAllLikes(postId));
    }
}
