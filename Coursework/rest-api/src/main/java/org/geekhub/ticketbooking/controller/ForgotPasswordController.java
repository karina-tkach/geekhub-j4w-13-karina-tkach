package org.geekhub.ticketbooking.controller;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import org.geekhub.ticketbooking.token.ForgotPasswordToken;
import org.geekhub.ticketbooking.user.User;
import org.geekhub.ticketbooking.token.ForgotPasswordTokenService;
import org.geekhub.ticketbooking.user.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;


@Controller
@SuppressWarnings("java:S1192")
public class ForgotPasswordController {
    private final UserService userService;

    private final ForgotPasswordTokenService forgotPasswordService;

    public ForgotPasswordController(UserService userService, ForgotPasswordTokenService forgotPasswordService) {
        this.userService = userService;
        this.forgotPasswordService = forgotPasswordService;
    }


    @GetMapping("/password-request")
    public String passwordRequest() {
        return "password-request";
    }

    @PostMapping("/password-request")
    public String savePasswordRequest(@RequestParam("username") String username, Model model) {
        User user = userService.getUserByEmail(username);
        if (user == null) {
            return setAttributesAndGetProperPage(model, "error",
                "This Email is not registered", "password-request");
        }

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.generateTokenForUser(user);

        ForgotPasswordToken token = forgotPasswordService.addToken(forgotPasswordToken);

        if (token == null) {
            return setAttributesAndGetProperPage(model, "error",
                "Unable to add token", "password-request");
        }


        try {
            forgotPasswordService.sendEmail(user.getEmail(), "Password Reset Link",
                forgotPasswordToken.getToken());
        } catch (MailException | MessagingException | UnsupportedEncodingException e) {
            return setAttributesAndGetProperPage(model, "error",
                "Error while sending email", "password-request");
        }

        return "redirect:/password-request?success";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@Param(value = "token") String token, Model model, HttpSession session) {
        session.setAttribute("token", token);
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.getTokenByValue(token);
        String errorMessage = forgotPasswordService.checkValidity(forgotPasswordToken);
        if (errorMessage != null) {
            return setAttributesAndGetProperPage(model, "error",
                errorMessage, "error-page");
        } else {
            return "reset-password";
        }

    }

    @PostMapping("/reset-password")
    public String saveResetPassword(@RequestParam(name = "password") String password, @RequestParam(name = "cPassword") String cPassword, HttpSession session, Model model) {
        if (!Objects.equals(password, cPassword)) {
            return setAttributesAndGetProperPage(model, "message",
                "Passwords don't match", "reset-password");
        }
        String token = (String) session.getAttribute("token");

        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.getTokenByValue(token);
        if (forgotPasswordToken == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Invalid token", "reset-password");
        }

        User user = forgotPasswordService.useToken(forgotPasswordToken, password);

        User updatedUser = userService.updateUserById(user, user.getId());
        if (updatedUser == null) {
            return setAttributesAndGetProperPage(model, "message",
                "Password must be at least 8 character long and contain at least 1 uppercase letter and 1 digit.",
                "reset-password");
        }
        forgotPasswordService.updateTokenById(forgotPasswordToken, forgotPasswordToken.getId());

        return setAttributesAndGetProperPage(model, "message",
            "You have successfully reset your password", "reset-password");
    }

    private String setAttributesAndGetProperPage(Model model, String attributeName,
                                                 String attributeValue, String page) {
        model.addAttribute(attributeName, attributeValue);
        return page;
    }
}
