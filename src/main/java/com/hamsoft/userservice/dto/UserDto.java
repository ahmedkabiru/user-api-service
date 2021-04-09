package com.hamsoft.userservice.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public record  UserDto(
        @NotBlank(message = "title is required")
        @JsonProperty("title")
        String title,

        @NotBlank(message = "firstname is required")
        @JsonProperty("firstName")
        String firstName,

        @NotBlank(message = "lastname is required")
        @JsonProperty("lastName")
        String lastName,

        @Email(message = "please provide a valid email address")
        @JsonProperty("email")
        String email,

        @NotBlank(message = "mobile number is required")
        @JsonProperty("mobile")
        String mobile,

        @NotBlank(message = "password is required")
        @JsonProperty("password")
        String password,

        @NotNull(message = "specify at least one role")
        @JsonProperty("role")
        String role
) { }
