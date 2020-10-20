package com.prateek.instagram.service;

import com.prateek.instagram.dto.UserDto;
import com.prateek.instagram.model.User;
import com.prateek.instagram.repository.UserRepository;
import com.prateek.instagram.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Override
    public Optional<UserDto> getUser(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(MapperUtil::buildUserDto);
    }

    @Override
    public String deleteUser(Long id) {
        userRepository.deleteById(id);

        return String.format("User with ID : %d deleted Successfully.", id);
    }

    @Override
    public UserDto addUser(UserDto userDto) throws DataIntegrityViolationException {

        User user = MapperUtil.buildUser(userDto);
        return MapperUtil.buildUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {

        User user = MapperUtil.buildUser(userDto);
        return MapperUtil.buildUserDto(userRepository.save(user));
    }
}
