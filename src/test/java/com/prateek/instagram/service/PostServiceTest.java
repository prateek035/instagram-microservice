package com.prateek.instagram.service;

import com.prateek.instagram.dto.ImageDto;
import com.prateek.instagram.dto.PostDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.exception.UserDoesNotExistException;
import com.prateek.instagram.model.Image;
import com.prateek.instagram.model.Post;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.ImageRepository;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PostServiceTest {

    ModelMapper modelMapper = new ModelMapper();
    UserDto userDto;
    User user;
    PostDto postDto;
    Post post;
    ImageDto imageDto;
    Image image;

    final PostRepository postRepository = Mockito.mock(PostRepository.class,
            Mockito.withSettings().verboseLogging());

    final UserRepository userRepository = Mockito.mock(UserRepository.class,
            Mockito.withSettings().verboseLogging());

    final ImageRepository imageRepository = Mockito.mock(ImageRepository.class,
            Mockito.withSettings().verboseLogging());

    @InjectMocks
    PostServiceImpl postService;

    @Before
    public void setUp()  {

        userDto = new UserDto().setId(10L)
                .setUserName("test_u1")
                .setFirstName("test")
                .setLastName("user")
                .setIsVerified(false)
                .setGender("male");

        user = modelMapper.map(userDto,User.class);

        imageDto = new ImageDto().setId(12L)
                .setImageURL("www.test-img.com");

        image = modelMapper.map(imageDto, Image.class);


        postDto = new PostDto().setId(11L)
                .setCaption("test_post")
                .setUser(userDto);

        post = modelMapper.map(postDto, Post.class);
    }

    @Test
    public void shouldFetchPostSuccess() {
        given(postRepository.findById(11L)).willReturn(Optional.of(post));

        Optional<PostDto> responsePostDto = postService.getPost(11L);

        assertEquals(postDto.getCaption(), responsePostDto.get().getCaption());
        assertEquals(postDto.getId(), responsePostDto.get().getId());
        assertEquals(postDto.getUser().getId(), responsePostDto.get().getUser().getId());
        assertEquals(postDto.getImageURLs(), responsePostDto.get().getImageURLs());

        verify(postRepository,times(1)).findById(any(Long.class));
    }

    @Test
    public void shouldFetchAllPostSuccess() {
        given(postRepository.findAllByUser(10L)).willReturn(Collections.singletonList(post));

        List<PostDto> responsePostDto = postService.getAllPosts(10L);

        assertEquals(1, responsePostDto.size());
        assertEquals(postDto.getCaption(), responsePostDto.get(0).getCaption());
        assertEquals(postDto.getId(), responsePostDto.get(0).getId());
        assertEquals(postDto.getUser().getId(), responsePostDto.get(0).getUser().getId());
        assertEquals(postDto.getImageURLs(), responsePostDto.get(0).getImageURLs());

        verify(postRepository,times(1)).findAllByUser(any(Long.class));
    }


    @Test
    public void shouldDeletePostSuccess() {
        postService.deletePost(11L);

        verify(postRepository,times(1)).deleteById(any(Long.class));
    }

    @Test
    public void shouldUpdatePostSuccess() {
        given(userRepository.findById(10L)).willReturn(Optional.of(user));
        given(postRepository.save(any(Post.class))).willReturn(post);

        try{
            PostDto responsePostDto = postService.updatePost(postDto, 10L);

            assertEquals(postDto.getCaption(), responsePostDto.getCaption());
            assertEquals(postDto.getId(), responsePostDto.getId());
            assertEquals(postDto.getUser().getId(), responsePostDto.getUser().getId());
            assertEquals(postDto.getImageURLs(), responsePostDto.getImageURLs());

        }catch(Exception e){
            fail();
        }

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void shouldThrowExceptionWhileUpdatePost() {

        given(userRepository.findById(10L)).willReturn(Optional.empty());
        given(postRepository.save(any(Post.class))).willReturn(post);

        try {
            postService.updatePost(postDto, 10L);
            fail();
        } catch (UserDoesNotExistException ignored){

        }
    }


    @After
    public void tearDown() {
        System.out.println("-----------------------TEST RUN COMPLETE------------------------");
    }

}