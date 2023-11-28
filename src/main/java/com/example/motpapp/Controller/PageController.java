package com.example.motpapp.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class PageController {
    @RequestMapping("/home")
    public String home() {
        return "home";
    }
}
