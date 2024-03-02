package org.geekhub.encryption.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.geekhub.encryption.users.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class UsersController {
    private final UserService userService;
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "Get all users")
    @GetMapping(path="/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }
}
