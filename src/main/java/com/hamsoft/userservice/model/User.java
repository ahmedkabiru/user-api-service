package com.hamsoft.userservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {


    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "date_registered",nullable = false)
    private LocalDateTime dateRegistered;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "token")
    private String token;

    @Column(name = "date_verified")
    private LocalDateTime dateVerified;

    @Column(name = "date_deactivated")
    private LocalDateTime dateDeactivated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;
}
