package com.prateek.instagram.service;

import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.UserRepository;
import com.prateek.instagram.util.MapperUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public ResponseDto<UserDto> getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()) {
            return MapperUtil.convertToResponseDto(200,
                    "User Found!",
                    MapperUtil.buildUserDto(optionalUser.get()));
        }else{
            return MapperUtil.convertToResponseDto(404,
                    "User Not Found!",
                    new UserDto());
        }
    }

    @Override
    public ResponseDto<Object> deleteUser(Long id) {
        userRepository.deleteById(id);

        return MapperUtil.convertToResponseDto(200,
                "User Deleted!",
                Collections.emptyList());
    }

    @Override
    public ResponseDto<UserDto> addUser(UserDto userDto) {
        try{
            User user = MapperUtil.buildUser(userDto);
            userRepository.save(user);
            return MapperUtil.convertToResponseDto(201,
                    "User created Successfully!", userDto);

        }catch(DataIntegrityViolationException e){
            return MapperUtil.convertToResponseDto(400,
                    "Username already taken!",userDto);
        }
    }

    @Override
    public ResponseDto<UserDto> updateUser(UserDto userDto) {
        try{
            User user = MapperUtil.buildUser(userDto);
            userRepository.save(user);
            return MapperUtil.convertToResponseDto(200,
                    "User Updated Successfully!", userDto);
        }catch(DataIntegrityViolationException e) {
            return MapperUtil.convertToResponseDto(400,
                    "Username already taken!", userDto);
        }
    }
}
