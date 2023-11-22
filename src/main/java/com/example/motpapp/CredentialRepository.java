package com.example.motpapp;

import com.example.motpapp.model.User;
import com.warrenstrange.googleauth.ICredentialRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CredentialRepository implements ICredentialRepository {

    private final Map<String, User> usersKeys = new HashMap<String, User>(){{
        put("admin@pk.com", null);
        put("moderator@pk.com", null);
    }};

    @Override
    public String getSecretKey(String userName) {
        return usersKeys.get(userName).getSecretKey();
    }

    @Override
    public void saveUserCredentials(String userName,
                                    String secretKey,
                                    int validationCode,
                                    List<Integer> scratchCodes) {
        usersKeys.put(userName, new User(userName, secretKey, validationCode, scratchCodes));
    }

    public User getUser(String username) {
        return usersKeys.get(username);
    }

}