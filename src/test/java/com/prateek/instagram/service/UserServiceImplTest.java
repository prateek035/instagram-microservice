package com.prateek.instagram.service;

import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.UserRepository;
import com.prateek.instagram.util.MapperUtil;
import org.junit.After;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceImplTest {


    ModelMapper modelMapper = new ModelMapper();

    private UserDto userDto;
    private User user;

    final UserRepository userRepository = Mockito.mock(UserRepository.class,
                                Mockito.withSettings().verboseLogging());

//    @Mock
//    UserRepository userRepository;

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

        given(userRepository.findById(10L)).willReturn(Optional.of(user));

        Optional<UserDto> responseUserDto = userService.getUser(10L);

        assertEquals(Optional.of(userDto), responseUserDto);
        verify(userRepository,times(1)).findById(any(Long.class));
    }

    @Test
    public void shouldAddUserSuccessfully() {

        given(userRepository.save(any(User.class))).willReturn(user);

        UserDto savedUser = userService.addUser(userDto);
        assertEquals(userDto, savedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowErrorWhenAddUserWithExistingUserName() {

        given(userRepository.findByUserName("test_u1")).willReturn(Optional.of(user));

        userService.addUser(userDto);
    }

    @Test
    public void shouldUpdateUserSuccessfully() {

        given(userRepository.save(any(User.class))).willReturn(user);

        UserDto updatedUserDto = userService.updateUser(userDto);

        assertEquals(userDto, updatedUserDto);
        verify(userRepository,times(1)).save(any(User.class));
    }

    @Test
    public void shouldDeleteUserSuccessFully() {

        userService.deleteUser(10L);
        userService.deleteUser(20L);

        verify(userRepository,times(2)).deleteById(any(Long.class));

    }

//    @After
//    public void teardown() {
//        System.out.println("::::::"+ mockingDetails(userRepository).printInvocations());
//    }


}