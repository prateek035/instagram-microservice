package com.prateek.instagram.service;

import com.prateek.instagram.dto.FollowMapDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.exception.UserDoesNotExistException;
import com.prateek.instagram.model.FollowMap;

import java.util.List;
import java.util.Optional;

public interface FollowService {

    List<UserDto> getFollower(Long userId) throws UserDoesNotExistException;
    List<UserDto> getFollowing(Long userId) throws UserDoesNotExistException;

    FollowMapDto followUser(Long userId, Long followId) throws UserDoesNotExistException;
    Object unfollowUser(Long userId, Long unfollowId) throws UserDoesNotExistException;
}
