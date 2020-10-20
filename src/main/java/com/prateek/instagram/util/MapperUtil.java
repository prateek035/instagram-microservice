package com.prateek.instagram.util;

import com.prateek.instagram.dto.CommentDto;
import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.model.Comment;
import com.prateek.instagram.model.Post;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.CommentRepository;
import com.prateek.instagram.repository.PostRepository;
import com.prateek.instagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

public class MapperUtil {


//    public static Comment buildComment(CommentDto commentDto) {
//
//        Optional<User> optionalUser = userRepository.findById(commentDto.getUserId());
//        Optional<Post> optionalPost = postRepository.findById(commentDto.getPostId());
//        Optional<Comment> optionalComment = commentRepository.findById(commentDto.getReplyId());
//
//        return new Comment().setReply(optionalComment.orElse(null))
//                .setUser(optionalUser.orElse(null))
//                .setPost(optionalPost.orElse(null))
//                .setDescription(commentDto.getDescription());
//    }

    public static CommentDto buildCommentDto(Comment comment) {

        return new CommentDto().setDescription(comment.getDescription())
                .setPostId(comment.getPost().getId())
                .setUserId(comment.getUser().getId())
                .setReplyId(comment.getReply() == null ? null : comment.getReply().getId());
    }

    public static UserDto buildUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setContactNo(user.getContactNo());
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPassword(user.getPassword());
        userDto.setIsVerified(user.isVerified());
        userDto.setGender(user.getGender());

        return userDto;
    }

    public static User buildUser(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setContactNo(userDto.getContactNo());
        user.setUserName(userDto.getUserName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setVerified(userDto.getIsVerified());
        user.setGender(userDto.getGender());

        return user;
    }

    public static <T> ResponseDto<T> convertToResponseDto(Integer status,
                                                          String description, T result) {

        ResponseDto<T> responseDto = new ResponseDto<T>();
        return responseDto.setStatus(status)
                .setDescription(description)
                .setResult(result);
    }



}
