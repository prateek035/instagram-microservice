package com.prateek.instagram.service;

import com.prateek.instagram.dto.CommentDto;
import com.prateek.instagram.dto.FollowMapDto;
import com.prateek.instagram.dto.PostDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.model.Comment;
import com.prateek.instagram.model.FollowMap;
import com.prateek.instagram.model.Post;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.FollowMapRepository;
import com.prateek.instagram.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FollowServiceImplTest {

    ModelMapper modelMapper = new ModelMapper();
    UserDto userDto;
    User user;

    UserDto followerDto;
    User follower;

    FollowMap followMap;
    FollowMapDto followMapDto;

    final UserRepository userRepository = Mockito.mock(UserRepository.class,
            Mockito.withSettings().verboseLogging());

    final FollowMapRepository followMapRepository = Mockito.mock(FollowMapRepository.class,
            Mockito.withSettings().verboseLogging());

    @InjectMocks
    FollowServiceImpl followService;

    @Before
    public void setUp()  {

        userDto = new UserDto().setId(10L)
                .setUserName("test_u1")
                .setFirstName("test")
                .setLastName("user")
                .setIsVerified(false)
                .setGender("male");

        user = modelMapper.map(userDto,User.class);

        followerDto = new UserDto().setId(20L)
                .setUserName("test_follower_u1")
                .setFirstName("follow_test")
                .setLastName("user")
                .setIsVerified(true)
                .setGender("female");

        follower = modelMapper.map(followerDto,User.class);

        followMapDto = new FollowMapDto().setFollower(followerDto)
                                                .setUser(userDto);

        followMap = new FollowMap().setFollower(follower).setUser(user).setId(21L);
    }

    @Test
    public void fetchAllFollowerSuccess() {

        given(userRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(user));
        given(followMapRepository.findAllByUser(any(User.class)))
                .willReturn(Collections.singletonList(followMap));

        try {
            List<UserDto> followers = followService.getFollower(10L);

            assertEquals(1, followers.size());
            assertEquals(follower.getId(), (Object) followers.get(0).getId());
        } catch(Exception e){
            fail();
        }

        verify(followMapRepository, times(1))
                                    .findAllByUser(any(User.class));
    }


    @Test
    public void fetchAllFollowingSuccess() {

        given(userRepository.findById(any(Long.class))).willReturn(Optional.ofNullable(follower));
        given(followMapRepository.findAllByFollower(any(User.class)))
                .willReturn(Collections.singletonList(followMap));

        try {
            List<UserDto> follows = followService.getFollowing(10L);

            assertEquals(1, follows.size());
            assertEquals(user.getId(), (Object) follows.get(0).getId());
        } catch(Exception e){
            fail();
        }

        verify(followMapRepository, times(1))
                .findAllByFollower(any(User.class));
    }

    @Test
    public void shouldAbleToFollowUserSuccess() {
        given(userRepository.findById(10L)).willReturn(Optional.ofNullable(user));
        given(userRepository.findById(20L)).willReturn(Optional.ofNullable(follower));

        given(followMapRepository.save(any(FollowMap.class))).willReturn(followMap);

        try {
            FollowMapDto saveFollowDto = followService.followUser(10L, 20L);

            assertEquals(userDto.getId(), saveFollowDto.getUser().getId());
            assertEquals(followerDto.getId(), saveFollowDto.getFollower().getId());
        } catch (Exception e) {
            fail();
        }

        verify(followMapRepository, times(1)).save(any(FollowMap.class));
    }

    @Test
    public void shouldBeAbleTOUnFollowUserSuccess() {
        given(userRepository.findById(10L)).willReturn(Optional.ofNullable(user));
        given(userRepository.findById(20L)).willReturn(Optional.ofNullable(follower));

        given(followMapRepository
                .existsByUserAndFollower(any(User.class),any(User.class)))
                .willReturn(true);
        given(followMapRepository
                .findByUserAndFollower(any(User.class),any(User.class)))
                .willReturn(followMap);

        try {
             followService.unfollowUser(10L, 20L);
        } catch (Exception e) {
            fail();
        }

        verify(followMapRepository, times(1)).delete(any(FollowMap.class));
        verify(followMapRepository, times(1))
                .findByUserAndFollower(any(User.class), any(User.class));
    }


    @After
    public void tearDown() {
        System.out.println("++++++++++++TEST ENDS HERE+++++++++++++++++");
    }
}