package com.oneworldaccuracy.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "first name is required")
    private String firstName;

    @NotBlank(message = "last name is required")
    private String lastName;

    @Email(message = "please provide a valid email address")
    private String email;

    @NotBlank(message = "mobile number is required")
    private String mobile;

    @NotBlank(message = "mobile number is required")
    private String password;

    @NotNull(message = "specify at least one role")
    private String role;

}
