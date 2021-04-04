package com.oneworldaccuracy.userservice.service;

import com.oneworldaccuracy.userservice.dto.UserDto;
import com.oneworldaccuracy.userservice.model.Role;
import com.oneworldaccuracy.userservice.model.User;
import com.oneworldaccuracy.userservice.model.UserStatus;
import com.oneworldaccuracy.userservice.repository.UserRepository;
import com.oneworldaccuracy.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;


/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */
//@ExtendWith(MockitoExtension.class)
class UserServiceTest {



    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    public void init() {
       MockitoAnnotations.openMocks(this);
       userService = new UserServiceImpl(userRepository, passwordEncoder);
    }
    public  UserDto getUserDTO() {
        UserDto userDto = new UserDto();
        userDto.setTitle("Mr");
        userDto.setFirstName("Ahmed");
        userDto.setLastName("Kabiru");
        userDto.setEmail("opeyemi.kabiru@yahoo.com");
        userDto.setRole("ADMIN");
        userDto.setMobile("08117713143");
        userDto.setPassword("123456");
        return userDto;
    }

    private User toUser(UserDto userDto) {
        var user = new User();
        user.setTitle(userDto.getTitle());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMobile(userDto.getMobile());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.valueOf(userDto.getRole()));
        user.setStatus(UserStatus.REGISTERED);
        user.setDateRegistered(LocalDateTime.now());
        return  user;
    }

    @Test
    void createUser() {
        UserDto userDto = getUserDTO();
        User user = toUser(userDto);
        Mockito.when(userRepository.save((any()))).thenReturn(user);
        User userCreated = userService.createUser(userDto);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any());
        assertEquals(user.getEmail(), userCreated.getEmail());
        assertEquals(user.getPassword(), userCreated.getPassword());
        assertEquals(user.getStatus(), userCreated.getStatus());
    }


    @Test
    void getAllUsers() {
        final Page<User> users = new PageImpl<>(new ArrayList<>());
        Mockito.when(userRepository.findAll(Mockito.any(Pageable.class))).thenReturn(users);
        final  Page<User> getUsers = userService.getAllUsers(PageRequest.of(1,1));
        assertThat(getUsers).isEmpty();
    }


}