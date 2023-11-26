package com.example.motpapp.Controller;

import com.example.motpapp.DTO.UserDto;
import com.example.motpapp.DTO.UserRegistrationDTO;
import com.example.motpapp.model.User;
import com.example.motpapp.model.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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
