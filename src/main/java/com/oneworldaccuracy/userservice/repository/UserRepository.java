package com.oneworldaccuracy.userservice.repository;

import com.oneworldaccuracy.userservice.model.User;
import com.oneworldaccuracy.userservice.repository.base.BasePagingRepository;

import java.util.Optional;

public interface UserRepository extends BasePagingRepository<User> {

    Optional<User> findByEmail(String email);
}