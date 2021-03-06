package com.hamsoft.userservice.controller;

import com.hamsoft.userservice.dto.UserDto;
import com.hamsoft.userservice.exception.BadRequestException;
import com.hamsoft.userservice.exception.NotFoundException;
import com.hamsoft.userservice.model.User;
import com.hamsoft.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */

@RestController
@RequestMapping("/api/users")
public record UserController(UserService userService) {

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = "application/json")
    public Page<User> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(pageable);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDTO) {
        if (userService.findByEmail(userDTO.email()).isPresent()) {
            throw new BadRequestException("Email is already Taken");
        }
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(value = "{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody UserDto userDTO) {
        User user = userService.findById(id).orElseThrow(() -> new NotFoundException("User not found with id " + id.toString()));
        userService.updateUser(user, userDTO);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deactivateUser(@PathVariable(value = "id") Long id) {
        User user = userService.findById(id).orElseThrow(() -> new NotFoundException("User not found with id " + id.toString()));
        userService.deactivateUser(user);
        return ResponseEntity.ok().build();
    }


    @GetMapping(path = "/verify")
    public ResponseEntity<Object> verifyUser(@RequestParam("token") String token) {
        User user = userService.findByToken(token).orElseThrow(() -> new NotFoundException("Token id " + token + "not found"));
        userService.verifyUser(user);
        return ResponseEntity.ok().build();
    }


}
