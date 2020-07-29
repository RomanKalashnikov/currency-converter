package ru.kalashnikov.test.currency_converter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String getUser(Map<String,Object> model){

        return "getUser";
    }
}
