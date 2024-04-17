package org.geekhub.ticketbooking.controller;

import org.geekhub.ticketbooking.model.Role;
import org.geekhub.ticketbooking.model.User;
import org.geekhub.ticketbooking.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("admin/users")
@SuppressWarnings("java:S1192")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewHomePageWithPagination(@RequestParam(defaultValue = "1") int page,
                                             @RequestParam(defaultValue = "7") int pageSize, Model model) {
        List<User> users = userService.getUsersWithPagination(page, pageSize);
        int rows = userService.getUsersRowsCount();

        if (rows == -1 || users.isEmpty()) {
            model.addAttribute("error", "Can't load users");
        } else {
            model.addAttribute("page", page);
            model.addAttribute("totalPages", Math.ceil(rows / (float) pageSize));
            model.addAttribute("listUsers", users);
        }

        return "users";
    }

    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "new_user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        User addedUser = userService.addUser(user);
        if (addedUser == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid user parameters", "new_user");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully added user", "new_user");
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        if (user == null) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot get user by this id", "update_user");
        }
        return "update_user";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user, Model model, Principal principal) {
        User oldUser = userService.getUserById(user.getId());
        User executor = userService.getUserByEmail(principal.getName());
        Role oldRole = oldUser.getRole();

        if ((executor.getRole() != Role.SUPER_ADMIN) && (oldRole == Role.SUPER_ADMIN)) {
            return setAttributesAndGetProperPage(model, "message",
                "Cannot change super admin", "update_user");
        }

        if ((executor.getRole() != Role.SUPER_ADMIN) && (oldRole != user.getRole())) {
            user.setRole(oldRole);
        }

        String password = user.getPassword();
        int userId = user.getId();

        User updatedUser;
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            updatedUser = userService.updateUserWithoutPasswordChangeById(user, userId);
        } else {
            updatedUser = userService.updateUserById(user, userId);
            if (updatedUser != null) {
                boolean result = userService.sendPasswordResetEmail(password, updatedUser.getEmail());

                if(!result) {
                    return setAttributesAndGetProperPage(model, "message",
                        "Cannot send email to user, but user was updated", "update_user");
                }
            }
        }

        if (updatedUser == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid user parameters", "update_user");
        }
        return setAttributesAndGetProperPage(model, "message",
            "You have successfully updated user", "update_user");
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") int id, Model model) {
        boolean result = userService.deleteUserById(id);
        if (!result) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot delete user by this id", "update_user");
        }
        return "redirect:/admin/users";
    }

    private String setAttributesAndGetProperPage(Model model, String attributeName,
                                                 String attributeValue, String page) {
        model.addAttribute(attributeName, attributeValue);
        return page;
    }
}
