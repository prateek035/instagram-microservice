package com.prateek.instagram.controller;

import com.prateek.instagram.dto.CommentDto;
import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.exception.CommentDoesNotExistException;
import com.prateek.instagram.exception.PostDoesNotExistException;
import com.prateek.instagram.exception.UserDoesNotExistException;
import com.prateek.instagram.service.CommentService;
import com.prateek.instagram.util.MapperUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/{userId}/post/{postId}")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public ResponseDto<List<CommentDto>> getAllComments(@PathVariable("postId") Long postId) {

        try{
            return MapperUtil.convertToResponseDto(200,
                    "List of comments for the post",
                    commentService.getAllComment(postId));

        }catch (PostDoesNotExistException e) {

            return MapperUtil.convertToResponseDto(404,
                    e.getMessage(),
                    null);
        }

    }

    @GetMapping("/comment/{commentId}")
    public ResponseDto<List<CommentDto>> getAllReply(@PathVariable("commentId") Long commentId) {


        try{
            return MapperUtil.convertToResponseDto(200,
                    "List of all Replies to Comment",
                    commentService.getAllReply(commentId));

        }catch (CommentDoesNotExistException e) {

            return MapperUtil.convertToResponseDto(404,
                    e.getMessage(),
                    null);
        }

    }

    @PostMapping("/comment")
    public ResponseDto<CommentDto> addComment(@PathVariable("userId") Long userId,
                                              @PathVariable("postId") Long postId,
                                              @RequestBody CommentDto commentDto) {

        try{
            return MapperUtil.convertToResponseDto(201,
                    "Comment added Successfully!",
                    commentService.addComment(userId, postId, commentDto));

        }catch (PostDoesNotExistException | UserDoesNotExistException e) {

            return MapperUtil.convertToResponseDto(404,
                    e.getMessage(),
                    null);
        }


    }

    @PutMapping("/comment/{commentId}")
    public ResponseDto<CommentDto> updateComment(@PathVariable("userId") Long userId,
                                                 @PathVariable("postId") Long postId,
                                                 @PathVariable("commentId") Long commentId,
                                                 @RequestBody CommentDto commentDto) {

        try{
            return MapperUtil.convertToResponseDto(200,
                    "Comment updated Success!",
                    commentService.updateComment(userId,postId,commentId,commentDto));

        }catch (PostDoesNotExistException | UserDoesNotExistException | CommentDoesNotExistException e) {

            return MapperUtil.convertToResponseDto(404,
                    e.getMessage(),
                    null);
        }
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseDto<Object> deleteComment(@PathVariable("commentId") Long commentId) {

        return MapperUtil.convertToResponseDto(200,
                "Comment deleted!",
                commentService.deleteComment(commentId));
    }
}
