package com.example.motpapp;

import com.example.motpapp.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OTPAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public OTPAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        // Extract OTP from the request
        String otp = obtainOTP(request);
        String password = obtainPassword(request);
        String username = obtainUsername(request);

        OTPAuthenticationToken authRequest = new OTPAuthenticationToken(otp, username, password);

        // Return the authentication token for further processing
        return getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainOTP(HttpServletRequest request) {
        return request.getParameter("otp");
    }
    private String obtainUsername(HttpServletRequest request) {
        return request.getParameter("username");
    }
    private String obtainPassword(HttpServletRequest request) {
        return request.getParameter("password");
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }





}
