package com.prateek.instagram.service;

import com.fasterxml.jackson.databind.cfg.MapperBuilder;
import com.prateek.instagram.dto.CommentDto;
import com.prateek.instagram.exception.CommentDoesNotExistException;
import com.prateek.instagram.exception.PostDoesNotExistException;
import com.prateek.instagram.exception.UserDoesNotExistException;
import com.prateek.instagram.model.Comment;
import com.prateek.instagram.model.Post;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.CommentRepository;
import com.prateek.instagram.repository.PostRepository;
import com.prateek.instagram.repository.UserRepository;
import com.prateek.instagram.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private  PostRepository postRepository;

    @Autowired
    private  CommentRepository commentRepository;

    @Autowired
    private  UserRepository userRepository;

    @Override
    public List<CommentDto> getAllComment(Long postId) throws PostDoesNotExistException {

        Optional<Post> optionalPost = postRepository.findById(postId);

        if(optionalPost.isEmpty()) {
            throw new PostDoesNotExistException();
        }

        return commentRepository.getAllByPost(optionalPost.get())
                .stream()
                .parallel()
                .map(MapperUtil::buildCommentDto)
                .filter(commentDto -> commentDto.getReplyId() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getAllReply(Long commentId) throws CommentDoesNotExistException {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if(optionalComment.isEmpty()) {
            throw new CommentDoesNotExistException();
        }

        return commentRepository.getAllByReplyId(commentId)
                .stream()
                .map(MapperUtil::buildCommentDto)
                .collect(Collectors.toList());

    }

    @Override
    public CommentDto addComment(Long userId, Long postId, CommentDto commentDto)
            throws PostDoesNotExistException, UserDoesNotExistException {

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<Comment>optionalComment = Optional.empty();

        if(commentDto.getReplyId() != null)
            optionalComment = commentRepository.findById(commentDto.getReplyId());

        if(optionalPost.isEmpty())
            throw new PostDoesNotExistException();

        if(optionalUser.isEmpty())
            throw new UserDoesNotExistException();

        return MapperUtil.buildCommentDto(commentRepository.save(
                new Comment().setDescription(commentDto.getDescription())
                .setReply(optionalComment.orElse(null))
                .setPost(optionalPost.get())
                .setUser(optionalUser.get())
        ));
    }

    @Override
    public Object deleteComment(Long commentId) {
         commentRepository.deleteById(commentId);
         return null;
    }

    @Override
    public CommentDto updateComment(Long userId, Long postId,
                                    Long commentId, CommentDto commentDto)
            throws PostDoesNotExistException, UserDoesNotExistException, CommentDoesNotExistException {

        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Post> optionalPost = postRepository.findById(postId);

        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if(optionalPost.isEmpty())
            throw new PostDoesNotExistException();

        if(optionalUser.isEmpty())
            throw new UserDoesNotExistException();

        if(optionalComment.isEmpty())
            throw new CommentDoesNotExistException();

        return MapperUtil.buildCommentDto(commentRepository.save(
                new Comment().setDescription(commentDto.getDescription())
                        .setId(commentId)
                        .setUser(optionalUser.get())
                        .setPost(optionalPost.get())
        ));

    }
}
