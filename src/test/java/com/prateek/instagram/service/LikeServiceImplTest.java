package com.prateek.instagram.service;

import com.prateek.instagram.dto.LikeDto;
import com.prateek.instagram.dto.PostDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.model.Like;
import com.prateek.instagram.model.Post;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.LikeRepository;
import com.prateek.instagram.repository.PostRepository;
import com.prateek.instagram.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LikeServiceImplTest {

    ModelMapper modelMapper = new ModelMapper();
    UserDto userDto;
    User user;
    PostDto postDto;
    Post post;
    LikeDto likeDto;
    Like like;

    final PostRepository postRepository = Mockito.mock(PostRepository.class,
            Mockito.withSettings().verboseLogging());

    final UserRepository userRepository = Mockito.mock(UserRepository.class,
            Mockito.withSettings().verboseLogging());

    final LikeRepository likeRepository = Mockito.mock(LikeRepository.class,
            Mockito.withSettings().verboseLogging());

    @InjectMocks
    LikeServiceImpl likeService;

    @Before
    public void setUp() {
        userDto = new UserDto().setId(10L)
                .setUserName("test_u1")
                .setFirstName("test")
                .setLastName("user")
                .setIsVerified(false)
                .setGender("male");

        user = modelMapper.map(userDto,User.class);

        postDto = new PostDto().setId(11L)
                .setCaption("test_post")
                .setUser(userDto);

        post = modelMapper.map(postDto, Post.class);

        likeDto = new LikeDto().setId(15L)
                .setPostId(11L)
                .setUserId(10L);

        like = new Like().setId(15L).setPost(post).setUserId(10L);
    }

    @Test
    public void shouldFetchAllLikesSuccess() {
        given(likeRepository.findAllByPostId(11L))
                .willReturn(Collections.singletonList(like));

        List<LikeDto> likeResponse = likeService.getAllLikes(11L);

        assertEquals(1, likeResponse.size());
        assertEquals(like.getId(), likeResponse.get(0).getId());
        assertEquals(like.getUserId(), likeResponse.get(0).getUserId());
        assertEquals(like.getPost().getId(), likeResponse.get(0).getPostId());

        verify(likeRepository, times(1))
                                .findAllByPostId(any(Long.class));

    }

    @Test
    public void shouldLikeAPostWhenAlreadyLikedSuccess() {
        given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));

        given(likeRepository.existsByPostIdAndUserId(any(Long.class), any(Long.class)))
                .willReturn(true);

        given(likeRepository.save(any(Like.class))).willReturn(like);

        try {
            LikeDto responseLike = likeService.likePost(10L, 11L);

            assertEquals(likeDto.getId(), responseLike.getId());
            assertEquals(likeDto.getPostId(), responseLike.getPostId());
            assertEquals(likeDto.getUserId(), responseLike.getUserId());

        } catch (Exception e) {
            fail();
        }

        verify(likeRepository, times(2))
                                .existsByPostIdAndUserId(any(Long.class),any(Long.class));

        verify(likeRepository, times(1)).save(any(Like.class));
        verify(likeRepository,times(1))
                .deleteByPostIdAndUserId(any(Long.class), any(Long.class));
    }

    @Test
    public void shouldLikeAPostFirstTimeSuccess() {
        given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));

        given(likeRepository.existsByPostIdAndUserId(any(Long.class), any(Long.class)))
                .willReturn(false);

        given(likeRepository.save(any(Like.class))).willReturn(like);

        try {
            LikeDto responseLike = likeService.likePost(10L, 11L);

            assertEquals(likeDto.getId(), responseLike.getId());
            assertEquals(likeDto.getPostId(), responseLike.getPostId());
            assertEquals(likeDto.getUserId(), responseLike.getUserId());

        } catch (Exception e) {
            fail();
        }
        verify(likeRepository, times(1))
                .existsByPostIdAndUserId(any(Long.class),any(Long.class));

        verify(likeRepository, times(1)).save(any(Like.class));
        verify(likeRepository,never())
                .deleteByPostIdAndUserId(any(Long.class), any(Long.class));
    }


    @Test
    public void shouldDislikeAPostSuccess() {
        given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));

        given(likeRepository.existsByPostIdAndUserId(any(Long.class), any(Long.class)))
                .willReturn(true);

        try {
            likeService.dislikePost(10L,11L);

        } catch (Exception e) {
            fail();
        }
        verify(likeRepository, times(1))
                .existsByPostIdAndUserId(any(Long.class),any(Long.class));

        verify(likeRepository, never()).save(any(Like.class));
        verify(likeRepository,times(1))
                .deleteByPostIdAndUserId(any(Long.class), any(Long.class));
    }

    @After
    public void tearDown() {
        System.out.println("-------------------TEST RUN COMPLETE------------------");
    }
}