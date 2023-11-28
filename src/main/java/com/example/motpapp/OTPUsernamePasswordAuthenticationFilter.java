package com.example.motpapp;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OTPUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String OTP_PARAMETER = "otp";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
        Authentication authentication = super.attemptAuthentication(request, response);

        // Check if the request includes an OTP parameter
        String otp = request.getParameter("otp");
        if (otp != null && !otp.isEmpty()) {
            // Convert the existing authentication to OTPAuthenticationToken
            return new OTPAuthenticationToken(otp, (String) authentication.getPrincipal(), (String) authentication.getCredentials());
        }

        return authentication;
    }

    private String obtainOTP(HttpServletRequest request) {
        return request.getParameter(OTP_PARAMETER);
    }

    private void setDetails(HttpServletRequest request, String otp) {
        request.getSession().setAttribute(OTP_PARAMETER, otp);
    }
}

