package com.oneworldaccuracy.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneworldaccuracy.userservice.dto.UserDto;
import com.oneworldaccuracy.userservice.exception.BadRequestException;
import com.oneworldaccuracy.userservice.exception.NotFoundException;
import com.oneworldaccuracy.userservice.model.User;
import com.oneworldaccuracy.userservice.model.UserStatus;
import com.oneworldaccuracy.userservice.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    User user;
    @BeforeEach
    public void init() {
        user = new User();
        user.setUserId(1L);
        user.setTitle("Mr");
        user.setFirstName("Ahmed");
        user.setMobile("08117713143");
        user.setLastName("Kabiru");
        user.setEmail("opeyemi.kabiru@yahoo.com");
        user.setPassword("12345678");
        user.setToken(UUID.randomUUID().toString());
        user.setStatus(UserStatus.REGISTERED);
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        given(userService.createUser(any(UserDto.class))).willReturn(user);
        var userDto = new UserDto();
        userDto.setFirstName("Ahmed");
        userDto.setMobile("08117713143");
        userDto.setLastName("Kabiru");
        userDto.setEmail("opeyemi.kabiru@yahoo.com");
        userDto.setPassword("12345678");
        this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getBadException_when_email_already_exist_on_user_create() throws Exception {
        var userDto = new UserDto();
        userDto.setFirstName("Ahmed");
        userDto.setMobile("08117713143");
        userDto.setLastName("Kabiru");
        userDto.setEmail("opeyemi.kabiru@yahoo.com");
        userDto.setPassword("12345678");
        given(userService.findByEmail(userDto.getEmail())).willReturn(Optional.of(user));
        this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        Long userId = 1L;
        var userDto = new UserDto();
        userDto.setFirstName("Ahmed");
        userDto.setMobile("08117713143");
        userDto.setLastName("Opeyemi");
        userDto.setEmail("opeyemi.kabiru@yahoo.com");
        userDto.setPassword("12345678");
        given(userService.findById(userId)).willReturn(Optional.of(user));
        this.mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

    }

    @Test
    void updateUserNotFound() throws Exception {
        Long userId = 2L;
        var userDto = new UserDto();
        userDto.setFirstName("Ahmed");
        userDto.setMobile("08117713143");
        userDto.setLastName("Kabiru");
        userDto.setEmail("opeyemi.kabiru@yahoo.com");
        userDto.setPassword("12345678");
        given(userService.findById(1L)).willReturn(Optional.of(user));
        this.mockMvc.perform(put("/api/users/{id}",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }


    @Test
     void getAllUsersShouldBeSuccessful() throws Exception {
       int page =0;
       int size = 1;
       Page<User> userList = new PageImpl<>(Collections.singletonList(user));
       given(userService.getAllUsers(PageRequest.of(page,size))).willReturn(userList);
        this.mockMvc.perform(get("/api/users")
                .param("page",String.valueOf(page))
                .param("size",String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].firstName", is(user.getFirstName())));
    }

    @Test
    void deactivateUserShouldBeSuccessful() throws  Exception{
        Long userId = 1L;
        given(userService.findById(userId)).willReturn(Optional.of(user));
        doNothing().when(userService).deactivateUser(user);
        this.mockMvc.perform(delete("/api/users/{id}", user.getUserId()))
                .andExpect(status().isOk());
        verify(userService, times(1)).deactivateUser(user);
    }

    @Test
    void deactivateUserNotFound() throws Exception {
        Long userId = 2L;
        given(userService.findById(1L)).willReturn(Optional.of(user));
        this.mockMvc.perform(delete("/api/users/{id}",userId))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }


    @Test
    void verifyUserShouldBeSuccessful() throws  Exception{
        String token = user.getToken();
        given(userService.findByToken(token)).willReturn(Optional.of(user));
        doNothing().when(userService).verifyUser(user);
        this.mockMvc.perform(
                get("/api/users/verify")
                        .param("token",token)
        ).andExpect(status().isOk());
        verify(userService, times(1)).verifyUser(user);
    }

    @Test
    void verifyUserTokenNotFound() throws Exception {
        given(userService.findByToken(user.getToken())).willReturn(Optional.of(user));
        this.mockMvc.perform(get("/api/users/verify")
                .param("token",UUID.randomUUID().toString()))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }





}