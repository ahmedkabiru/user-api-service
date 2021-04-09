package com.hamsoft.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @project onewa-user-service
 * @Author kabiruahmed on 04/04/2021
 */

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException  extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
