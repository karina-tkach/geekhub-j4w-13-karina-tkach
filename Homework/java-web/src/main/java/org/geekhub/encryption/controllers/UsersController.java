package org.geekhub.encryption.controllers;

import org.geekhub.encryption.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {
    private final UserService userService;
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path="/users")
    public String users() {
        return "users";
    }
}
