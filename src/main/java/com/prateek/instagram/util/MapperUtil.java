package com.prateek.instagram.util;

import com.prateek.instagram.dto.ResponseDto;
import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.model.User;

public class MapperUtil {

    public static UserDto buildUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setContactNo(user.getContactNo());
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPassword(user.getPassword());
        userDto.setIsVerified(user.isVerified());
        userDto.setGender(user.getGender());

        return userDto;
    }

    public static User buildUser(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setContactNo(userDto.getContactNo());
        user.setUserName(userDto.getUserName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setVerified(userDto.getIsVerified());
        user.setGender(userDto.getGender());

        return user;
    }

    public static <T> ResponseDto<T> convertToResponseDto(Integer status,
                                                          String description, T result) {

        ResponseDto<T> responseDto = new ResponseDto<T>();
        return responseDto.setStatus(status)
                .setDescription(description)
                .setResult(result);
    }



}
