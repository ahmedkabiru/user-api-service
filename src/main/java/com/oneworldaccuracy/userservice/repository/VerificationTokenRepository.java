package com.oneworldaccuracy.userservice.repository;

import com.oneworldaccuracy.userservice.model.VerificationToken;
import com.oneworldaccuracy.userservice.repository.base.BaseRepository;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */

public interface VerificationTokenRepository extends BaseRepository<VerificationToken> {

    VerificationToken findByToken(String token);
}
