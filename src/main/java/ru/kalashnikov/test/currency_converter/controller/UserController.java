package ru.kalashnikov.test.currency_converter.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kalashnikov.test.currency_converter.entity.User;
import ru.kalashnikov.test.currency_converter.service.UserService;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@NoArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getUser(Map<String, Object> model) {
        List<User> userList = userService.getAllUsers();
        model.put("user", userList);

        return "users";
    }

    @PostMapping
    public User saveUser(User user, Map<String ,Object> model){
        model.put("User",user);
        userService.saveUser(user);
        return user;
    }

}
