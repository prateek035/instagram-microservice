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
import com.prateek.instagram.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements LikeService{

    @Autowired
    private  PostRepository postRepository;

    @Autowired
    private  LikeRepository likeRepository;

    @Autowired
    private  UserRepository userRepository;

    @Override
    public List<LikeDto> getAllLikes(Long postId) {

        return likeRepository.findAllByPostId(postId)
                .stream()
                .map(MapperUtil::buildLikeDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String dislikePost(Long userId, Long postId) throws PostDoesNotExistException, LikeDoesNotExistException {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if(optionalPost.isEmpty()) {
            throw new PostDoesNotExistException();
        }

        if(!likeRepository.existsByPostIdAndUserId(postId,userId)) {
            throw new LikeDoesNotExistException();
        }

        likeRepository.deleteByPostIdAndUserId(postId,userId);
        return String.format("User: %d disliked post : %d", userId, postId);
    }

    // Removes older like and insert a new like with fresh timestamp.
    @Override
    @Transactional
    public LikeDto likePost(Long userId, Long postId) throws PostDoesNotExistException, LikeDoesNotExistException,
                                                            UserDoesNotExistException {

        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()) {
            throw new UserDoesNotExistException();
        }

        if(optionalPost.isEmpty()) {
            throw new PostDoesNotExistException();
        }

        if(likeRepository.existsByPostIdAndUserId(postId, userId)) {
            dislikePost(userId, postId);
        }
        return MapperUtil.buildLikeDto(likeRepository.save(new Like(optionalPost.get(), userId)));
    }
}
