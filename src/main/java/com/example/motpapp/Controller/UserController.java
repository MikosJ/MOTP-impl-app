package com.example.motpapp.Controller;

import com.example.motpapp.DTO.UserRegistrationDTO;
import com.example.motpapp.model.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public void register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        var existingUser = userRepository.findByUsername(userRegistrationDTO.getUsername());
        if (existingUser.isPresent()) {
            var userToSave = existingUser.get();
            userToSave.setPassword(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()));
            userRepository.save(userToSave);
        }
    }
}
