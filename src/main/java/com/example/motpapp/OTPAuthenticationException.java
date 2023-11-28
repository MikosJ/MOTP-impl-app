package com.example.motpapp;

import org.springframework.security.core.AuthenticationException;

public class OTPAuthenticationException extends AuthenticationException {

    public OTPAuthenticationException(String msg) {
        super(msg);
    }
}
