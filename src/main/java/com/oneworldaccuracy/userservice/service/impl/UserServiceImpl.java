package com.oneworldaccuracy.userservice.service.impl;

import com.oneworldaccuracy.userservice.dto.UserDto;
import com.oneworldaccuracy.userservice.model.User;
import com.oneworldaccuracy.userservice.repository.UserRepository;
import com.oneworldaccuracy.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */
@Service
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserDto userDto) {

    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return null;
    }

    @Override
    public void updateUser(UserDto userDto) {

    }

    @Override
    public void deactivateUser(User user) {

    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }
}
