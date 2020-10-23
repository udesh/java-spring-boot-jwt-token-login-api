package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Authentication Exception
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends Exception {

   
	private static final long serialVersionUID = 1L;

    public AuthenticationException(String message) {
        super(message);
    }
}
