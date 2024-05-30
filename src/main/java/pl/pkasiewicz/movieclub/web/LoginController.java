package pl.pkasiewicz.movieclub.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class LoginController {

    @GetMapping("/login")
    public String loginFrom() {
        return "login-form";
    }
}
