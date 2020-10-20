package com.prateek.instagram.controller;

import com.prateek.instagram.dto.FollowMapDto;
import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.exception.UserDoesNotExistException;
import com.prateek.instagram.service.FollowService;
import com.prateek.instagram.util.MapperUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/{userId}")
public class FollowMapController {

    private FollowService followService;


    public FollowMapController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/followers")
    public ResponseDto<List<UserDto>> getAllFollowerUser(@PathVariable("userId") Long userId) {


        try {
            return MapperUtil.convertToResponseDto(200,
                    "List of your followers",
                    followService.getFollower(userId));

        } catch (UserDoesNotExistException e){
            return MapperUtil.convertToResponseDto(404,
                    "User Not found.",
                    null);
        }

    }

    @GetMapping("/followings")
    public ResponseDto<List<UserDto>> getAllFollowingUser(@PathVariable("userId") Long userId) {

        try{
            return MapperUtil.convertToResponseDto(200,
                    "List of User you followed.",
                    followService.getFollowing(userId));

        }catch(UserDoesNotExistException e) {
            return MapperUtil.convertToResponseDto(404,
                    "User Not found.",
                    null);
        }

    }

    @PostMapping("/follow/{followId}")
    public ResponseDto<FollowMapDto> followUser(@PathVariable("userId") Long userId,
                                                @PathVariable("followId") Long followId) {

        try{
            return MapperUtil.convertToResponseDto(200,
                    "Followed Success.",
                    followService.followUser(userId,followId));

        }catch(UserDoesNotExistException e) {
            return MapperUtil.convertToResponseDto(404,
                    e.getMessage(),
                    null);
        }


    }

    @DeleteMapping("/follow/{followId}")
    public ResponseDto<Object> unfollowUser(@PathVariable("userId") Long userId,
                                            @PathVariable("followId") Long unfollowId) {

        try{
            return MapperUtil.convertToResponseDto(200,
                    "Followed Success.",
                    followService.unfollowUser(userId,unfollowId));

        }catch(UserDoesNotExistException e) {
            return MapperUtil.convertToResponseDto(404,
                    e.getMessage(),
                    null);
        }
    }

}
