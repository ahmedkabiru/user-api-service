package com.oneworldaccuracy.userservice.controller;

import com.oneworldaccuracy.userservice.dto.UserDto;
import com.oneworldaccuracy.userservice.exception.BadRequestException;
import com.oneworldaccuracy.userservice.exception.NotFoundException;
import com.oneworldaccuracy.userservice.model.User;
import com.oneworldaccuracy.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = "application/json")
    public Page<User> getAllUsers(
            @RequestParam( value = "page" ,defaultValue = "0" ,required = false) int page,
            @RequestParam(value = "size" ,defaultValue = "10" ,required = false) int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(pageable);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDTO){
        if (userService.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Email is already Taken");
        }
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping(value = "{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody UserDto userDTO) {
        User user = userService.findById(id).orElseThrow(() -> new NotFoundException("User id "+ id.toString() + "not found"));
        userService.updateUser(user, userDTO);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping(value = "{id}")
    public ResponseEntity<Object> deactivateUser( @PathVariable(value = "id") Long id) {
        User user = userService.findById(id).orElseThrow(() ->new NotFoundException("User id "+ id.toString() + "not found"));
        userService.deactivateUser(user);
        return ResponseEntity.ok().build();
    }




}
