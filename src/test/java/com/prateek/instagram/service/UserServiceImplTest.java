package com.prateek.instagram.service;

import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {


    ModelMapper modelMapper = new ModelMapper();

    private UserDto userDto;
    private User user;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Before
    public void setup() {
         userDto = new UserDto().setId(10L)
                .setUserName("test_u1")
                .setFirstName("test")
                .setLastName("user")
                .setGender("male")
                .setIsVerified(false);

         user = modelMapper.map(userDto,User.class);
    }

    @Test
    public void shouldFetchUserSuccessfully(){
        // Given
        given(userRepository.findById(10L)).willReturn(Optional.of(user));
        // when
        Optional<UserDto> responseUserDto = userService.getUser(10L);
        // then
        assertEquals(Optional.of(userDto), responseUserDto);
        verify(userRepository).findById(any(Long.class));
    }

    @Test
    public void shouldAddUserSuccessfully() {
        // Given
        given(userRepository.save(any(User.class))).willReturn(user);
         //when
        UserDto savedUser = userService.addUser(userDto);
        // then
        assertEquals(userDto, savedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void shouldUpdateUserSuccessfully() {
        //Given
        given(userRepository.save(any(User.class))).willReturn(user);
        //when
        UserDto updatedUserDto = userService.updateUser(userDto);
        //then
        assertEquals(userDto, updatedUserDto);
        verify(userRepository,times(1)).save(any(User.class));
    }

    @Test
    public void shouldDeleteUserSuccessFully() {
        //Given
        //when
        userService.deleteUser(10L);
        userService.deleteUser(20L);
        //then
        verify(userRepository,times(2)).deleteById(any(Long.class));

    }


}