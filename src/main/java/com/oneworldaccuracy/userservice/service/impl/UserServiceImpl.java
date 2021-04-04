package com.oneworldaccuracy.userservice.service.impl;

import com.oneworldaccuracy.userservice.dto.UserDto;
import com.oneworldaccuracy.userservice.model.Role;
import com.oneworldaccuracy.userservice.model.User;
import com.oneworldaccuracy.userservice.model.UserStatus;
import com.oneworldaccuracy.userservice.repository.UserRepository;
import com.oneworldaccuracy.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */
@Service
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;

    private  final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User createUser(UserDto userDto) {
        var user = new User();
        user.setTitle(userDto.getTitle());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setMobile(userDto.getMobile());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.valueOf(userDto.getRole()));
        user.setDateRegistered(LocalDateTime.now());
        user.setStatus(UserStatus.REGISTERED);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void updateUser(UserDto userDto) {

    }

    @Override
    public void deactivateUser(User user) {

    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
