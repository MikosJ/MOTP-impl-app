package com.example.motpapp.Controller;

import com.example.motpapp.DTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class PageController {
    @GetMapping("/home")
    public String home(@ModelAttribute("user") UserDto userDto,
                       BindingResult result,
                       Model model) {
        return "home";
    }

}
