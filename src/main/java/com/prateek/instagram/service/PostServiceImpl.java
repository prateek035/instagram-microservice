package com.prateek.instagram.service;

import com.prateek.instagram.dto.ImageDto;
import com.prateek.instagram.dto.PostDto;
import com.prateek.instagram.dto.UserDto;

import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.model.Image;
import com.prateek.instagram.model.Post;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.ImageRepository;
import com.prateek.instagram.repository.PostRepository;
import com.prateek.instagram.repository.UserRepository;
import com.prateek.instagram.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }


    @Override
    public ResponseDto<PostDto> getPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if(optionalPost.isPresent()) {
            return MapperUtil.convertToResponseDto(200,
                    "Post Found!",
                    modelMapper.map(optionalPost.get(),PostDto.class));
        }else {
            return MapperUtil.convertToResponseDto(404,
                    "Post Not Found!",
                    new PostDto());
        }
    }

    @Override
    public ResponseDto<List<PostDto>> getAllPosts(Long userId) {
        List<PostDto> posts = postRepository.findAllByUser(userId)
                                .stream()
                                .map(post -> modelMapper.map(post,PostDto.class))
                                .collect(Collectors.toList());

        return MapperUtil.convertToResponseDto(200,"Post found success!",posts);
    }

    @Override
    public ResponseDto<Object> deletePost(Long postId) {
        postRepository.deleteById(postId);

        return MapperUtil.convertToResponseDto(200,
                "Post Deleted!",
                Collections.emptyList());
    }

    @Override
    public ResponseDto<PostDto> addPost(PostDto postDto, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()) {
            postDto.setUser(modelMapper.map(optionalUser.get(),UserDto.class));
            Post post = postRepository.save(modelMapper.map(postDto,Post.class));

            ImageDto imageDto = new ImageDto();
            List<ImageDto> imageDtoList = postDto.getImageURLs().
                    stream().map(imageUrl -> {
                return imageDto.setPostId(post.getId()).setImageURL(imageUrl);
            }).collect(Collectors.toList());

            imageDtoList.forEach(image -> imageRepository.save(modelMapper.map(image, Image.class)) );

            return MapperUtil.convertToResponseDto(201,
                    "Post added Success!",
                    postDto);
        }else{
            return MapperUtil.convertToResponseDto(404,
                    "User Not Found!",new PostDto());
        }
    }

    @Override
    public ResponseDto<PostDto> updatePost(PostDto postDto, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isPresent()) {
            postDto.setUser(modelMapper.map(optionalUser.get(),UserDto.class));

            postRepository.save(modelMapper.map(postDto,Post.class));

            return MapperUtil.convertToResponseDto(200,
                    "Post update Success!",postDto);

        }else{
            return MapperUtil.convertToResponseDto(404,
                    "User does not exist.", new PostDto());
        }
    }
}
