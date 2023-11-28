package com.example.motpapp;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class OTPAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final String otp;

    public OTPAuthenticationToken(String otp) {
        super(null, null);
        this.otp = otp;
    }

    public String getOTP() {
        return otp;
    }
}
