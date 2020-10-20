package com.prateek.instagram.service;

import com.prateek.instagram.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> getUser(Long id);
    String deleteUser(Long id);
    UserDto addUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
}
