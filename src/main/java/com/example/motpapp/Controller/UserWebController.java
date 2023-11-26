package com.example.motpapp.Controller;

import com.example.motpapp.DTO.UserDto;
import com.example.motpapp.Service.OTPService;
import com.example.motpapp.model.User;
import com.example.motpapp.model.UserRepository;
import com.google.zxing.WriterException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Base64;

@Controller
public class UserWebController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final OTPService otpService;

    public UserWebController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, OTPService otpService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.otpService = otpService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping("/register/confirm")
    public String showRegistrationConfirm(@ModelAttribute("user") UserDto userDto, BindingResult result,
                                          Model model) throws IOException, WriterException {
        model.addAttribute("user", userDto);
        model.addAttribute("qrCode", Base64.getEncoder()
                .encodeToString(otpService.generateQRCode(userDto.getUsername())));
        return "registerConfirm";
    }

    @PostMapping("/save")
    public String registration(@ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        var existingUser = userRepository.findByUsername(userDto.getUsername());
        if (existingUser.isPresent()) {
            var userToSave = existingUser.get();
            userToSave.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
            userRepository.save(userToSave);
        }
        return "redirect:/register?success";
    }
}
