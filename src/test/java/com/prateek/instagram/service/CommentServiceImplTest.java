package com.prateek.instagram.service;

import com.prateek.instagram.dto.CommentDto;
import com.prateek.instagram.dto.ImageDto;
import com.prateek.instagram.dto.PostDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.exception.CommentDoesNotExistException;
import com.prateek.instagram.exception.PostDoesNotExistException;
import com.prateek.instagram.model.Comment;
import com.prateek.instagram.model.Image;
import com.prateek.instagram.model.Post;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.CommentRepository;
import com.prateek.instagram.repository.ImageRepository;
import com.prateek.instagram.repository.PostRepository;
import com.prateek.instagram.repository.UserRepository;
import com.prateek.instagram.util.MapperUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CommentServiceImplTest {

    ModelMapper modelMapper = new ModelMapper();
    UserDto userDto;
    User user;
    PostDto postDto;
    Post post;
    CommentDto commentDto;
    Comment comment;

    CommentDto replyDto;
    Comment reply;

    final PostRepository postRepository = Mockito.mock(PostRepository.class,
            Mockito.withSettings().verboseLogging());

    final UserRepository userRepository = Mockito.mock(UserRepository.class,
            Mockito.withSettings().verboseLogging());

    final CommentRepository commentRepository = Mockito.mock(CommentRepository.class,
            Mockito.withSettings().verboseLogging());

    @InjectMocks
    CommentServiceImpl commentService;

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

        commentDto = new CommentDto()
                .setDescription("Testing comment-Section")
                .setPostId(11L)
                .setUserId(10L);

        comment = new Comment().setDescription("Testing comment-Section")
                .setId(12L)
                .setPost(post)
                .setUser(user);

        replyDto = new CommentDto()
                .setDescription("Testing reply-Section")
                .setPostId(11L)
                .setUserId(10L);

        reply = new Comment().setDescription("Testing reply-Section")
                .setId(13L)
                .setReply(comment)
                .setPost(post)
                .setUser(user);

    }

    @Test
    public void shouldFetchAllCommentsSuccess() {
        given(postRepository.findById(11L)).willReturn(Optional.of(post));
        given(commentRepository.getAllByPost(post))
                .willReturn(Arrays.asList(comment, reply));

        try {
            List<CommentDto> responseCommentDtos = commentService.getAllComment(11L);

            assertEquals(1, responseCommentDtos.size());
            assertEquals(11L, (Object)responseCommentDtos.get(0).getPostId());
            assertNull(responseCommentDtos.get(0).getReplyId());

        } catch(PostDoesNotExistException e){
            fail();
        }

        verify(postRepository,times(1))
                                        .findById(any(Long.class));
        verify(commentRepository, times(1))
                                        .getAllByPost(any(Post.class));

    }

    @Test
    public void shouldFetchAllReplySuccess() {
        given(commentRepository.findById(12L)).willReturn(Optional.of(reply));
        given(commentRepository.getAllByReplyId(12L))
                .willReturn(Collections.singletonList(reply));

        try {
            List<CommentDto> responseCommentDtos = commentService.getAllReply(12L);

            assertEquals(1, responseCommentDtos.size());
            assertEquals(11L, (Object)responseCommentDtos.get(0).getPostId());
            assertNotNull(responseCommentDtos.get(0).getReplyId());
            assertEquals(12L,(Object)responseCommentDtos.get(0).getReplyId());

        } catch( CommentDoesNotExistException e){
            fail();
        }

        verify(postRepository,never())
                .findById(any(Long.class));
        verify(commentRepository, times(1))
                .findById(any(Long.class));
        verify(commentRepository, times(1))
                .getAllByReplyId(any(Long.class));
    }

    @Test
    public void shouldAddCommentSuccess() {

        given(userRepository.findById(any(Long.class)))
                .willReturn(Optional.of(user));
        given(postRepository.findById(any(Long.class)))
                .willReturn(Optional.of(post));

        given(commentRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        given(commentRepository.save(any(Comment.class)))
                .willReturn(comment);

        try {
            CommentDto savedCommentDto = commentService.addComment(10L,
                                                                11L,
                                                                 commentDto);

            assertEquals(comment.getUser().getId(), savedCommentDto.getUserId());
            assertNull(savedCommentDto.getReplyId());
            assertEquals(comment.getPost().getId(), savedCommentDto.getPostId());
            assertEquals(comment.getDescription(), savedCommentDto.getDescription());

        } catch( Exception e) {
            fail();
        }

        verify(commentRepository, times(1)).save(any(Comment.class));

    }

    @Test
    public void shouldAddReplySuccess() {

        given(userRepository.findById(any(Long.class)))
                .willReturn(Optional.of(user));
        given(postRepository.findById(any(Long.class)))
                .willReturn(Optional.of(post));

        given(commentRepository.findById(any(Long.class)))
                .willReturn(Optional.of(comment));

        given(commentRepository.save(any(Comment.class)))
                .willReturn(reply);

        try {
            CommentDto savedCommentDto = commentService.addComment(10L,
                    11L,
                    replyDto);

            System.out.println(reply);
            System.out.println(savedCommentDto);

            assertEquals(reply.getUser().getId(), savedCommentDto.getUserId());
            assertEquals(reply.getReply().getId(), savedCommentDto.getReplyId());
            assertEquals(reply.getPost().getId(), savedCommentDto.getPostId());
            assertEquals(reply.getDescription(), savedCommentDto.getDescription());

        } catch( Exception e) {
            fail();
        }

        verify(commentRepository, times(1)).save(any(Comment.class));

    }

    @Test
    public void shouldDeleteCommentOrReplySuccess() {
        commentService.deleteComment(13L);

        verify(commentRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void shouldUpdateCommentOrReplySuccess() {
        given(userRepository.findById(any(Long.class))).willReturn(Optional.of(user));
        given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));
        given(commentRepository.findById(any(Long.class))).willReturn(Optional.of(comment));
        given(commentRepository.save(any(Comment.class)))
                .willReturn(reply.setDescription("post desc. got changed"));

        try {
            CommentDto savedCommentDto = commentService.updateComment(10L,
                    11L, 12L, commentDto);

            assertEquals("post desc. got changed", savedCommentDto.getDescription());
            assertEquals(reply.getReply().getId(),savedCommentDto.getReplyId());
            assertEquals(reply.getPost().getId(), savedCommentDto.getPostId());
            assertEquals(reply.getUser().getId(), savedCommentDto.getUserId());

        } catch (Exception e) {
            fail();
        }

        verify(commentRepository, times(1)).save(any(Comment.class));
    }


    @After
    public void tearDown() {
        System.out.println("-------------------TEST CASE ENDS---------------");
    }
}