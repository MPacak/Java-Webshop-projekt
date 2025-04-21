package hr.webshop.controller;

import hr.webshop.dto.UserDto;
import hr.webshop.iservice.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/webshop")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDto userDto,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userDto.setRole("USER");
        userService.register(userDto);
        return "redirect:/webshop/login";
    }
}
