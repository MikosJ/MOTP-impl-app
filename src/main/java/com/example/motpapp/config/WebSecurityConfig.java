package com.example.motpapp.config;


import com.example.motpapp.AppAuthenticationSuccessHandler;
import com.example.motpapp.OTPAuthenticationProvider;
import com.example.motpapp.OTPUsernamePasswordAuthenticationFilter;
import com.example.motpapp.model.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static javax.management.Query.and;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private GoogleAuthenticator gAuth;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests((authorize) -> authorize
                        .requestMatchers(
                                new AntPathRequestMatcher("/home/**"),
                                new AntPathRequestMatcher("/index/**"),
                                new AntPathRequestMatcher("/continue/**")
                        ).authenticated()
                        .requestMatchers(
                                new AntPathRequestMatcher("/register/**"),
                                new AntPathRequestMatcher("/save/**"),
                                new AntPathRequestMatcher("/login/**"),
                                new AntPathRequestMatcher("/css/**")
                        ).permitAll())
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/home", true)
                                .failureUrl("/login?error=true")
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                ).addFilterAfter(otpUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(otpAuthenticationProvider());
    }

    @Bean
    public OTPUsernamePasswordAuthenticationFilter otpUsernamePasswordAuthenticationFilter() throws Exception {
        OTPUsernamePasswordAuthenticationFilter filter = new OTPUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public OTPAuthenticationProvider otpAuthenticationProvider() {
        return new OTPAuthenticationProvider(gAuth, userRepository, passwordEncoder());
    }

    @Bean
    public AppAuthenticationSuccessHandler appAuthenticationSuccessHandler(){
        return new AppAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(otpAuthenticationProvider());

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
