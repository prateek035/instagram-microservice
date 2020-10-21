package com.prateek.instagram.util;

import com.prateek.instagram.dto.*;
import com.prateek.instagram.model.*;
import org.modelmapper.ModelMapper;

public class MapperUtil {

    private static final ModelMapper modelMapper = new ModelMapper();

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

    public static FollowMap buildFollowMap(FollowMapDto followMapDto) {
        return modelMapper.map(followMapDto, FollowMap.class);
    }

    public static FollowMapDto buildFollowMapDto(FollowMap followMap) {
        return modelMapper.map(followMap, FollowMapDto.class);
    }

    public static Like buildLike(LikeDto likeDto) {
        return modelMapper.map(likeDto, Like.class);
    }

    public static LikeDto buildLikeDto(Like like) {
        return modelMapper.map(like,LikeDto.class);
    }

    public static Post buildPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    public static PostDto buildPostDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    public static Image buildImage(ImageDto imageDto) {
        return modelMapper.map(imageDto, Image.class);
    }

    public static ImageDto buildImageDto(Image image) {
        return modelMapper.map(image,ImageDto.class);
    }

    public static <T> ResponseDto<T> convertToResponseDto(Integer status,
                                                          String description, T result) {

        ResponseDto<T> responseDto = new ResponseDto<T>();
        return responseDto.setStatus(status)
                .setDescription(description)
                .setResult(result);
    }



}
