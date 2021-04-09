package com.hamsoft.userservice.service;

import com.hamsoft.userservice.dto.UserDto;
import com.hamsoft.userservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */
public interface UserService {

    User createUser(UserDto userDto);

    Page<User> getAllUsers(Pageable pageable);

    void updateUser(User user, UserDto userDto);

    void deactivateUser(User user);

    void verifyUser(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByToken(String token);
}
