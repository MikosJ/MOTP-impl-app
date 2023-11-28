package com.example.motpapp;

import com.example.motpapp.model.User;
import com.example.motpapp.model.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLOutput;
import java.util.Collections;

public class OTPAuthenticationProvider implements AuthenticationProvider {

    private final GoogleAuthenticator gAuth;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public OTPAuthenticationProvider(GoogleAuthenticator gAuth, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.gAuth = gAuth;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            return authenticateOTP(authentication);
    }

    @NotNull
    private Authentication authenticateOTP(Authentication authentication) throws AuthenticationException {
        String otp = authentication.getCredentials().toString();
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getAuthorities());
        String username = authentication.getName();  // Use getName() instead of getUsername()
        System.out.println("blabsalbsal");
        User user = userRepository.findByUsername(username).orElseThrow(() -> new OTPAuthenticationException("User not found"));

        if (gAuth.authorizeUser(username, Integer.parseInt(otp))) {
            // Compare passwords using matches instead of equals
            System.out.println(passwordEncoder.encode(authentication.getCredentials().toString()));
            if (passwordEncoder.matches(authentication.getCredentials().toString(),user.getPassword())) {
                System.out.println("Password matches");
                return new UsernamePasswordAuthenticationToken(
                        authentication.getPrincipal(), authentication.getCredentials(), Collections.emptyList());
            } else {
                System.out.println("Password does not match");
                throw new OTPAuthenticationException("Invalid Password");
            }
        } else {
            System.out.println("Otp does not match");
            throw new OTPAuthenticationException("Invalid OTP");
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return OTPAuthenticationToken.class.isAssignableFrom(authentication)
                || UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
