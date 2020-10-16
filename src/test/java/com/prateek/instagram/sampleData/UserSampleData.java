package com.prateek.instagram.sampleData;

import com.prateek.instagram.dto.UserDto;
import org.hibernate.type.TrueFalseType;

public class UserSampleData {

    public static UserDto getNewUserDto() {
        UserDto userDto = new UserDto();
        userDto.setGender("Male");
        userDto.setIsVerified(Boolean.TRUE);
        userDto.setPassword("testPaas");
        userDto.setFirstName("Prateek");
        userDto.setLastName("Mittal");
        userDto.setUserName("pm18");
        userDto.setContactNo("9897612475");
        userDto.setId(101);
        return userDto;
    }

    public static UserDto getNewUpdateUserDto() {
        UserDto userDto = new UserDto();

        userDto.setId(101);
        userDto.setFirstName("Pramod");

        return userDto;
    }
}
