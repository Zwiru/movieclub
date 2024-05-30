package pl.pkasiewicz.movieclub.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pkasiewicz.movieclub.domain.user.UserService;
import org.springframework.ui.Model;
import pl.pkasiewicz.movieclub.domain.user.dto.UserRegistrationDto;

import java.util.List;

@Controller
class RegistrationController {
    private final UserService userService;

    RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "registration-form";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegistrationDto userRegistration, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration-form";
        } else {
            userService.registerUserWithDefaultRole(userRegistration);
            return "redirect:/";
        }
    }
}
