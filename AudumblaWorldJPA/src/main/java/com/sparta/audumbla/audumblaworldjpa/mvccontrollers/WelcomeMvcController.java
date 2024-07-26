package com.sparta.audumbla.audumblaworldjpa.mvccontrollers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/welcome")
public class WelcomeMvcController {

    @GetMapping
    public String getWelcome(Model model) {
        return "welcomes/welcome";
    }
}