package org.geekhub.ticketbooking.controller;

import jakarta.mail.MessagingException;
import org.geekhub.ticketbooking.model.User;
import org.geekhub.ticketbooking.service.MailSenderService;
import org.geekhub.ticketbooking.service.UserService;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("admin/users")
@SuppressWarnings("java:S1192")
public class UsersController {
    private final UserService userService;
    private final MailSenderService mailSenderService;

    public UsersController(UserService userService, MailSenderService mailSenderService) {
        this.userService = userService;
        this.mailSenderService = mailSenderService;
    }

    @GetMapping
    public String viewHomePage(Model model) {
        model.addAttribute("listUsers", userService.getUsers());
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
    public String showFormForUpdate(@PathVariable( value = "id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        if (user == null) {
            return setAttributesAndGetProperPage(model, "error",
                "Cannot get user by this id", "update_user");
        }
        return "update_user";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user, Model model) {
        String password = user.getPassword();
        int userId = user.getId();

        User updatedUser;
        if(user.getPassword() == null || user.getPassword().isBlank()) {
            updatedUser = userService.updateUserWithoutPasswordChangeById(user, userId);
        }
        else {
            updatedUser = userService.updateUserById(user,userId);
            if(updatedUser != null) {
                String mail = "<p>Hello!</p>"
                    + "Your password was changed by administrator"
                    + "<br>"
                    + "<p>Your new password: " + password + "</p>"
                    + "<br>"
                    + "You can always reset it!";
                try {
                    mailSenderService.sendEmail(updatedUser.getEmail(), "Password Change By Admin", mail);
                } catch (MailException | MessagingException | UnsupportedEncodingException e) {
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
        if(!result) {
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
