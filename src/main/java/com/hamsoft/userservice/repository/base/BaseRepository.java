package com.hamsoft.userservice.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */

@NoRepositoryBean
public interface BaseRepository <T> extends JpaRepository<T, Long> {
}
