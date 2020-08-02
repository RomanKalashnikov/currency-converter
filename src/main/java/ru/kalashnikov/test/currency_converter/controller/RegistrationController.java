package ru.kalashnikov.test.currency_converter.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kalashnikov.test.currency_converter.entity.User;
import ru.kalashnikov.test.currency_converter.service.UserService;

import java.util.Map;

@Controller
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        model.put("New User", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam User user, Map<String, Object> model) {
        model.put("User", user);
        userService.saveUser(user);

        return "redirect:/login";
    }

}
