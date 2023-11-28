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
        if (authentication instanceof OTPAuthenticationToken) {
            return authenticateOTP((OTPAuthenticationToken) authentication);
        } else {
            throw new OTPAuthenticationException("Cannot authenticate " + authentication.getClass());
        }
    }

    @NotNull
    private Authentication authenticateOTP(OTPAuthenticationToken authentication) throws AuthenticationException {
        String otp = authentication.getOTP();
        String username = authentication.getName();  // Use getName() instead of getUsername()
        User user = userRepository.findByUsername(username).orElseThrow(() -> new OTPAuthenticationException("User not found"));

        if (gAuth.authorizeUser(username, Integer.parseInt(otp))) {
            // Compare passwords using matches instead of equals
            if (passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
                return new UsernamePasswordAuthenticationToken(
                        authentication.getPrincipal(), authentication.getCredentials(), Collections.emptyList());
            } else {
                throw new OTPAuthenticationException("Invalid Password");
            }
        } else {
            throw new OTPAuthenticationException("Invalid OTP");
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return OTPAuthenticationToken.class.isAssignableFrom(authentication)
                || UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
