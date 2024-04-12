package org.geekhub.ticketbooking.controller;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import org.geekhub.ticketbooking.model.ForgotPasswordToken;
import org.geekhub.ticketbooking.model.User;
import org.geekhub.ticketbooking.repository.interfaces.ForgotPasswordRepository;
import org.geekhub.ticketbooking.service.ForgotPasswordService;
import org.geekhub.ticketbooking.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;


@Controller
public class ForgotPasswordController {

    private final UserService userService;

    private final ForgotPasswordService forgotPasswordService;

    private final ForgotPasswordRepository forgotPasswordRepository;

    public ForgotPasswordController(UserService userService, ForgotPasswordService forgotPasswordService,
                                    ForgotPasswordRepository forgotPasswordRepository) {
        this.userService = userService;
        this.forgotPasswordService = forgotPasswordService;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }


    @GetMapping("/password-request")
    public String passwordRequest() {

        return "password-request";
    }

    @PostMapping("/password-request")
    public String savePasswordRequest(@RequestParam("username") String username, Model model) {
        User user = userService.getUserByEmail(username);
        if (user == null) {
            model.addAttribute("error", "This Email is not registered");
            return "password-request";
        }

        ForgotPasswordToken forgotPasswordToken = new ForgotPasswordToken();
        forgotPasswordToken.setExpireTime(forgotPasswordService.expireTimeRange());
        forgotPasswordToken.setToken(forgotPasswordService.generateToken());
        forgotPasswordToken.setUser(user);
        forgotPasswordToken.setUsed(false);

        forgotPasswordRepository.addToken(forgotPasswordToken, user.getId());

        String emailLink = "http://localhost:8089/reset-password?token=" + forgotPasswordToken.getToken();

        try {
            forgotPasswordService.sendEmail(user.getEmail(), "Password Reset Link", emailLink);
        } catch (MailException | MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", "Error While Sending email");
            return "password-request";
        }

        return "redirect:/password-request?success";
    }

    @GetMapping("/reset-password")
    public String resetPassword(@Param(value = "token") String token, Model model, HttpSession session) {

        session.setAttribute("token", token);
        ForgotPasswordToken forgotPasswordToken = forgotPasswordRepository.findByToken(token);
        return forgotPasswordService.checkValidity(forgotPasswordToken, model);

    }

    @PostMapping("/reset-password")
    public String saveResetPassword(@RequestParam(name = "password") String password, @RequestParam(name = "cPassword") String cPassword, HttpSession session, Model model) {
        if(!Objects.equals(password, cPassword)) {
            model.addAttribute("message", "Passwords don't match");
            return "reset-password";
        }
        String token = (String) session.getAttribute("token");

        ForgotPasswordToken forgotPasswordToken = forgotPasswordRepository.findByToken(token);
        User user = forgotPasswordToken.getUser();
        user.setPassword(password);
        forgotPasswordToken.setUsed(true);
        User updatedUser = userService.updateUserById(user, user.getId());
        if (updatedUser == null) {
            model.addAttribute("message", "Password must be at least 8 character long and contain at least 1 uppercase letter and 1 digit.");
            return "reset-password";
        }
        forgotPasswordRepository.updateTokenById(forgotPasswordToken, forgotPasswordToken.getId(), user.getId());

        model.addAttribute("message", "You have successfully reset your password");

        return "reset-password";
    }
}
