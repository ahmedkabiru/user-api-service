package com.oneworldaccuracy.userservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class VerificationToken {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    public VerificationToken(){
        this.token = UUID.randomUUID().toString();
        this.createdDate = LocalDateTime.now();
        this.expiryDate = this.createdDate.plusDays(1);
    }

}
