package com.example.motpapp;

import com.example.motpapp.model.User;
import com.example.motpapp.model.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.ICredentialRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CredentialRepository implements ICredentialRepository {

    private final UserRepository userRepository;

    public CredentialRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getSecretKey(String userName) {
        User demoUser = userRepository.findByUsername(userName).orElseThrow();
        return demoUser.getSecretKey();
    }

    @Override
    public void saveUserCredentials(String userName,
                                    String secretKey,
                                    int validationCode,
                                    List<Integer> scratchCodes) {
        userRepository.save(User.builder()
                .username(userName)
                .secretKey(secretKey)
                .validationCode(validationCode)
                .build());
    }

    public boolean validateCode(String username, int code) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getValidationCode() == code;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

}