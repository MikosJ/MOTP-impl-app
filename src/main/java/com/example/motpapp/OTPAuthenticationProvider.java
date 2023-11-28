package com.example.motpapp;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class OTPAuthenticationProvider implements AuthenticationProvider {

    private final GoogleAuthenticator gAuth;

    public OTPAuthenticationProvider(GoogleAuthenticator gAuth) {
        this.gAuth = gAuth;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println(authentication.getName());
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getPrincipal());
        String otp = (String) authentication.getCredentials();

        if (gAuth.authorizeUser(authentication.getName(), Integer.parseInt(otp))) {
            return new UsernamePasswordAuthenticationToken(
                    authentication.getName(), null, authentication.getAuthorities());
        } else {
            throw new OTPAuthenticationException("Invalid OTP");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OTPAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
