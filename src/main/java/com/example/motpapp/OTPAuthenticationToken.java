package com.example.motpapp;

import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Getter
public class OTPAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String otp;

    public OTPAuthenticationToken(String otp, String username, String password) {
        super(username, password);
        this.otp = otp;
    }

    public String getOTP() {
        return otp;
    }

}
