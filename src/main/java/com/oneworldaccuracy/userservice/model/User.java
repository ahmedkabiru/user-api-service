package com.oneworldaccuracy.userservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Column(name = "user_id", nullable = false)
    @Id
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

    @Column(name = "date_registered", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateRegistered;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "date_verified",columnDefinition = "TIMESTAMP")
    private LocalDateTime dateVerified;

    @Column(name = "date_deactivated", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateDeactivated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;
    
}
