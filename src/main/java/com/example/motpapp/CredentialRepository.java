package com.example.motpapp;

import com.example.motpapp.model.User;
import com.warrenstrange.googleauth.ICredentialRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CredentialRepository implements ICredentialRepository {

    private final Map<String, User> usersKeys = new HashMap<>(){{
        put("admin@pk.com", new User("admin","secret", 123,List.of(123,456, 789)));
        put("moderator@pk.com", new User("moderator","secret", 321,List.of(987,654, 321)));
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