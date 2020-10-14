package com.prateek.instagram.service;

import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.dto.UserDto;

public interface UserService {
    ResponseDto<UserDto> getUser(Long id);
    ResponseDto<Object> deleteUser(Long id);
    ResponseDto<UserDto> addUser(UserDto userDto);
    ResponseDto<UserDto> updateUser(UserDto userDto);
}
