package com.example.ooadgroupproject.Exception;


import org.springframework.security.core.AuthenticationException;

public class LoginFailedException extends AuthenticationException {


    public LoginFailedException(String msg) {
        super(msg);
    }
}
