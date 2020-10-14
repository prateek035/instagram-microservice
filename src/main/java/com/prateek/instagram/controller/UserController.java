package com.prateek.instagram.controller;

import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.model.User;
import com.prateek.instagram.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseDto<UserDto> getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PutMapping
    public ResponseDto<UserDto> updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @PostMapping
    public ResponseDto<UserDto> addUser(@RequestBody UserDto user) {
        return userService.addUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Object> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }
}
