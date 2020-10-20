package com.prateek.instagram.controller;

import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.service.UserService;
import com.prateek.instagram.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping("/{id}")
    public ResponseDto<UserDto> getUser(@PathVariable("id") Long id) {

        Optional<UserDto> optionalUserDto = userService.getUser(id);

        if(optionalUserDto.isPresent()) {
            return MapperUtil.convertToResponseDto(200,
                    "User Found!",
                    optionalUserDto.get());
        }else{
            return MapperUtil.convertToResponseDto(404,
                    "User Not Found!",
                    null);
        }
    }

    @PutMapping
    public ResponseDto<UserDto> updateUser(@RequestBody UserDto userDto) {

        try{
            return MapperUtil.convertToResponseDto(200,
                    "User Updated Successfully!", userService.updateUser(userDto));
        }catch(DataIntegrityViolationException e) {
            return MapperUtil.convertToResponseDto(400,
                    "Username already taken!", null);
        }
    }

    @PostMapping
    public ResponseDto<UserDto> addUser(@RequestBody UserDto user) {
        try{
            return MapperUtil.convertToResponseDto(201,
                    "User created Successfully!", userService.addUser(user));

        }catch(DataIntegrityViolationException e){
            return MapperUtil.convertToResponseDto(400,
                    "Username already taken!",null);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseDto<String> deleteUser(@PathVariable("id") Long id) {

        return MapperUtil.convertToResponseDto(200,
                "User Deleted!",
                userService.deleteUser(id));
    }
}
