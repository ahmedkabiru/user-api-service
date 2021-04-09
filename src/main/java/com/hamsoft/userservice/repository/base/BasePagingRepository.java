package com.hamsoft.userservice.repository.base;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */

@NoRepositoryBean
public interface BasePagingRepository <T> extends PagingAndSortingRepository<T, Long> {
}

