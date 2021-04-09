package com.hamsoft.userservice.repository;

import com.hamsoft.userservice.model.User;
import com.hamsoft.userservice.repository.base.BasePagingRepository;

import java.util.Optional;

public interface UserRepository extends BasePagingRepository<User> {

    Optional<User> findByEmail(String email);

    Optional<User> findByToken(String token);
}