package com.prateek.instagram.service;

import com.prateek.instagram.dto.LikeDto;
import com.prateek.instagram.exception.LikeDoesNotExistException;
import com.prateek.instagram.exception.PostDoesNotExistException;
import com.prateek.instagram.exception.UserDoesNotExistException;
import com.prateek.instagram.model.Like;
import com.prateek.instagram.model.Post;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.LikeRepository;
import com.prateek.instagram.repository.PostRepository;
import com.prateek.instagram.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService{

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public LikeServiceImpl(PostRepository postRepository, LikeRepository likeRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<LikeDto> getAllLikes(Long postId) {

        return likeRepository.findAllByPostId(postId)
                .stream()
                .map(like -> modelMapper.map(like, LikeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Object dislikePost(Long userId, Long postId) throws PostDoesNotExistException, LikeDoesNotExistException {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if(optionalPost.isEmpty()) {
            throw new PostDoesNotExistException(String.format("Post : %d does not exist.",postId));
        }

        if(!likeRepository.existsByPostIdAndUserId(postId,userId)) {
            throw new LikeDoesNotExistException("You need to like before disliking post.");
        }

        likeRepository.deleteByPostIdAndUserId(postId,userId);
        return null;
    }

    // Removes older like and insert a new like with fresh timestamp.
    @Override
    @Transactional
    public LikeDto likePost(Long userId, Long postId) throws PostDoesNotExistException, LikeDoesNotExistException,
                                                            UserDoesNotExistException {

        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()) {
            throw new UserDoesNotExistException(String.format("User : %d does not exist",userId));
        }

        if(optionalPost.isEmpty()) {
            throw new PostDoesNotExistException(String.format("Post : %d does not exist.",postId));
        }

        if(likeRepository.existsByPostIdAndUserId(postId, userId)) {
            dislikePost(userId, postId);
        }
        return modelMapper.map(likeRepository.save(new Like(optionalPost.get(), userId)), LikeDto.class);

    }
}
