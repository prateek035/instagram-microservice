package com.prateek.instagram.service;

import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {


    ModelMapper modelMapper = new ModelMapper();

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void ShouldFetchUserSuccessfully(){

        // Given
        UserDto userDto = new UserDto().setId(10L)
                                .setUserName("test_u1")
                                .setFirstName("test")
                                .setLastName("user")
                                .setGender("male")
                                .setIsVerified(false);

        User user = modelMapper.map(userDto,User.class);

        given(userRepository.findById(10L)).willReturn(Optional.of(user));

        // when
        Optional<UserDto> responseUserDto = userService.getUser(10L);

        // then
        assertEquals(Optional.of(userDto), responseUserDto);
    }


}