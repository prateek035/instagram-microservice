package com.prateek.instagram.service;

import com.prateek.instagram.dto.FollowMapDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.exception.UserDoesNotExistException;
import com.prateek.instagram.model.FollowMap;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.FollowMapRepository;
import com.prateek.instagram.repository.UserRepository;
import com.prateek.instagram.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService{

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private FollowMapRepository followMapRepository;

    @Override
    public List<UserDto> getFollower(Long userId) throws UserDoesNotExistException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty())
            throw new UserDoesNotExistException();

        return followMapRepository.findAllByUser(optionalUser.get())
                .stream()
                .map(FollowMap::getFollower)
                .map(MapperUtil::buildUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getFollowing(Long userId) throws UserDoesNotExistException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty())
            throw new UserDoesNotExistException();

        return followMapRepository.findAllByFollower(optionalUser.get())
                .stream()
                .map(FollowMap::getUser)
                .map(MapperUtil::buildUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public FollowMapDto followUser(Long userId, Long followId)
                                        throws UserDoesNotExistException {

        Optional<User> user = userRepository.findById(userId);
        Optional<User> followUser = userRepository.findById(followId);

        if(user.isEmpty())
            throw new UserDoesNotExistException();

        if(followUser.isEmpty())
            throw new UserDoesNotExistException("Target User does not exist");

        return MapperUtil.buildFollowMapDto(followMapRepository.save(new FollowMap()
                                .setFollower(followUser.get())
                                .setUser(user.get())));
    }

    @Override
    public Object unfollowUser(Long userId, Long unfollowId) throws UserDoesNotExistException {
        Optional<User> user = userRepository.findById(userId);
        Optional<User> unFollowUser = userRepository.findById(unfollowId);

        if(user.isEmpty())
            throw new UserDoesNotExistException();

        if(unFollowUser.isEmpty())
            throw new UserDoesNotExistException("Target User does not exist");

        if(!followMapRepository.existsByUserAndFollower(user.get(), unFollowUser.get()))
            throw new UserDoesNotExistException("Mapping does not exist");

        followMapRepository.delete(followMapRepository
                .findByUserAndFollower(user.get(), unFollowUser.get()));

        return null;
    }
}
