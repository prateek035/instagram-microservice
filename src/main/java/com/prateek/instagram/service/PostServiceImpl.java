package com.prateek.instagram.service;

import com.prateek.instagram.dto.ImageDto;
import com.prateek.instagram.dto.PostDto;

import com.prateek.instagram.exception.UserDoesNotExistException;
import com.prateek.instagram.model.Post;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.ImageRepository;
import com.prateek.instagram.repository.PostRepository;
import com.prateek.instagram.repository.UserRepository;
import com.prateek.instagram.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private  PostRepository postRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  ImageRepository imageRepository;


    @Override
    public Optional<PostDto> getPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        return optionalPost.map(MapperUtil::buildPostDto);
    }

    @Override
    public List<PostDto> getAllPosts(Long userId) {

        return postRepository.findAllByUser(userId)
                                .stream()
                                .map(MapperUtil::buildPostDto)
                                .collect(Collectors.toList());

    }

    @Override
    public String deletePost(Long postId) {
        postRepository.deleteById(postId);

        return String.format("Post with ID : %d deleted Successfully", postId);
    }

    @Override
    public PostDto addPost(PostDto postDto, Long userId) throws UserDoesNotExistException {

        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty())
            throw new UserDoesNotExistException();

            postDto.setUser(MapperUtil.buildUserDto(optionalUser.get()));

            Post post = postRepository.save(MapperUtil.buildPost(postDto));

            ImageDto imageDto = new ImageDto();
            List<ImageDto> imageDtoList = postDto.getImageURLs()
                    .stream()
                    .map(imageUrl ->  imageDto.setPostId(post.getId()).setImageURL(imageUrl))
                    .collect(Collectors.toList());

            imageDtoList.forEach(image -> imageRepository.save(MapperUtil.buildImage(image)) );

            return MapperUtil.buildPostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long userId) throws UserDoesNotExistException {

        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty())
            throw new UserDoesNotExistException();

        postDto.setUser(MapperUtil.buildUserDto(optionalUser.get()));

        return MapperUtil.buildPostDto(postRepository.save(MapperUtil.buildPost(postDto)));

    }
}
