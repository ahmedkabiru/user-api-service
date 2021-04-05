package com.oneworldaccuracy.userservice.service.impl;

import com.oneworldaccuracy.userservice.dto.UserDto;
import com.oneworldaccuracy.userservice.model.Role;
import com.oneworldaccuracy.userservice.model.User;
import com.oneworldaccuracy.userservice.model.UserStatus;
import com.oneworldaccuracy.userservice.repository.UserRepository;
import com.oneworldaccuracy.userservice.service.MailService;
import com.oneworldaccuracy.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

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
        user.setToken(UUID.randomUUID().toString());
        user.setRole(Role.valueOf(userDto.getRole()));
        user.setDateRegistered(LocalDateTime.now());
        user.setStatus(UserStatus.REGISTERED);
        var  createUser = userRepository.save(user);
        mailService.sendVerificationEmail(createUser);
        return createUser;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void updateUser(User user, UserDto userDto) {
        user.setTitle(userDto.getTitle());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setMobile(userDto.getMobile());
        user.setRole(Role.valueOf(userDto.getRole()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deactivateUser(User user) {
       user.setStatus(UserStatus.DEACTIVATED);
       user.setDateDeactivated(LocalDateTime.now());
       userRepository.save(user);
       mailService.sendDeactivationEmail(user);
    }

    @Transactional
    @Override
    public void verifyUser(User user) {
        user.setVerified(true);
        user.setStatus(UserStatus.VERIFIED);
        user.setDateVerified(LocalDateTime.now());
        user.setToken(null);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }
}
