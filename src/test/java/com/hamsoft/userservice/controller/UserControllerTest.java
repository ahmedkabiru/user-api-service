package com.hamsoft.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hamsoft.userservice.dto.UserDto;
import com.hamsoft.userservice.exception.BadRequestException;
import com.hamsoft.userservice.exception.NotFoundException;
import com.hamsoft.userservice.model.User;
import com.hamsoft.userservice.model.UserStatus;
import com.hamsoft.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private User user;
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
        MockHttpServletResponse response = this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void assertBadRequestStatusIfUserAlreadyRegistered() throws Exception {
        var userDto = new UserDto();
        userDto.setFirstName("Ahmed");
        userDto.setMobile("08117713143");
        userDto.setLastName("Kabiru");
        userDto.setEmail("opeyemi.kabiru@yahoo.com");
        userDto.setPassword("12345678");
        given(userService.findByEmail(userDto.getEmail())).willReturn(Optional.of(user));
        MvcResult result = this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertTrue(result.getResolvedException() instanceof BadRequestException);
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
        MockHttpServletResponse response =    this.mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
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
        MvcResult result = this.mockMvc.perform(put("/api/users/{id}",userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertTrue(result.getResolvedException() instanceof NotFoundException);
    }


    @Test
     void getAllUsersShouldBeSuccessful() throws Exception {
       int page =0;
       int size = 1;
       Page<User> userList = new PageImpl<>(Collections.singletonList(user));
       given(userService.getAllUsers(PageRequest.of(page,size))).willReturn(userList);
        MockHttpServletResponse response = this.mockMvc.perform(get("/api/users")
                .param("page",String.valueOf(page))
                .param("size",String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].firstName", is(user.getFirstName()))).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void deactivateUserShouldBeSuccessful() throws  Exception{
        Long userId = 1L;
        given(userService.findById(userId)).willReturn(Optional.of(user));
        doNothing().when(userService).deactivateUser(user);
        MockHttpServletResponse response =  mockMvc.perform(delete("/api/users/{id}", user.getUserId())).andReturn().getResponse();
        verify(userService, times(1)).deactivateUser(user);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void deactivateUserNotFound() throws Exception {
        Long userId = 2L;
        given(userService.findById(1L)).willReturn(Optional.of(user));
        MvcResult result =  this.mockMvc.perform(delete("/api/users/{id}",userId))
                .andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertTrue(result.getResolvedException() instanceof NotFoundException);
    }


    @Test
    void verifyUserShouldBeSuccessful() throws  Exception{
        String token = user.getToken();
        given(userService.findByToken(token)).willReturn(Optional.of(user));
        doNothing().when(userService).verifyUser(user);
        MockHttpServletResponse response = this.mockMvc.perform(
                get("/api/users/verify")
                        .param("token",token)
        ).andReturn().getResponse();
        verify(userService, times(1)).verifyUser(user);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void verifyUserTokenNotFound() throws Exception {
        given(userService.findByToken(user.getToken())).willReturn(Optional.of(user));
        MvcResult result =  this.mockMvc.perform(get("/api/users/verify")
                .param("token",UUID.randomUUID().toString())).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }





}