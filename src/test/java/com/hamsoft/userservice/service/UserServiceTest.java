package com.hamsoft.userservice.service;

import com.hamsoft.userservice.dto.UserDto;
import com.hamsoft.userservice.model.Role;
import com.hamsoft.userservice.model.User;
import com.hamsoft.userservice.model.UserStatus;
import com.hamsoft.userservice.repository.UserRepository;
import com.hamsoft.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;


/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    private UserService userService;

    @BeforeEach
    public void init() {
       userService = new UserServiceImpl(userRepository, passwordEncoder,mailService);
    }
    public UserDto getUserDTO() {
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
        user.setUserId(1L);
        user.setTitle(userDto.getTitle());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMobile(userDto.getMobile());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.valueOf(userDto.getRole()));
        user.setStatus(UserStatus.REGISTERED);
        user.setToken(UUID.randomUUID().toString());
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
        Mockito.verify(mailService).sendVerificationEmail(userCreated);
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


    @Test
    void findById() {
        UserDto userDto = getUserDTO();
        User user = toUser(userDto);
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Optional<User> getUserById = userService.findById(1L);
        assertTrue(getUserById.isPresent());
        assertEquals(user,getUserById.get());
    }

    @Test
    void updateUser() {
        User user = toUser(getUserDTO());
        UserDto userDtoUpdated = getUserDTO();
        userDtoUpdated.setMobile("08033292804");
        user.setMobile(userDtoUpdated.getMobile());
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        userService.updateUser(user,userDtoUpdated);
        Optional<User> userOptional = userRepository.findById(user.getUserId());
        Mockito.verify(userRepository).save(any(User.class));
        assertTrue(userOptional.isPresent());
        assertThat(userOptional.get().getMobile()).isEqualTo(userDtoUpdated.getMobile());
    }


    @Test
    void activateUser() {
        User user = toUser(getUserDTO());
        user.setStatus(UserStatus.VERIFIED);
        user.setToken(null);
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        userService.verifyUser(user);
        Optional<User> userOptional = userRepository.findById(user.getUserId());
        Mockito.verify(userRepository).save(any(User.class));
        assertTrue(userOptional.isPresent());
        assertThat(userOptional.get().getStatus()).isEqualTo(UserStatus.VERIFIED);
        assertThat(userOptional.get().getToken()).isNull();
    }

    @Test
    void deactivateUser() {
        User user = toUser(getUserDTO());
        userService.deactivateUser(user);
        Mockito.verify(userRepository).save(any(User.class));
        Mockito.verify(mailService).sendDeactivationEmail(user);
    }

    @Test
    void findByEmail() {
        UserDto userDto = getUserDTO();
        User user = toUser(userDto);
        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        Optional<User> getUserById = userService.findByEmail("opeyemi.kabiru@yahoo.com");
        assertTrue(getUserById.isPresent());
        assertEquals(user,getUserById.get());
    }

    @Test
    void findByToken() {
        UserDto userDto = getUserDTO();
        User user = toUser(userDto);
        Mockito.when(userRepository.findByToken(any())).thenReturn(Optional.of(user));
        Optional<User> getUserById = userService.findByToken(user.getToken());
        assertTrue(getUserById.isPresent());
        assertEquals(user.getToken(),getUserById.get().getToken());
    }
}