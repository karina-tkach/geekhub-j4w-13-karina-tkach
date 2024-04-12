package org.geekhub.ticketbooking.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.geekhub.ticketbooking.model.Role;
import org.geekhub.ticketbooking.model.User;
import org.geekhub.ticketbooking.security.SecurityUserDetails;
import org.geekhub.ticketbooking.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("users/register")
    public User register(@RequestBody User user, HttpServletRequest request) {
        user.setRole(Role.USER);
        User newUser = userService.addUser(user);
        if (newUser != null) {
            authenticate(newUser, request);
        }
        return newUser;
    }
    public void authenticate(User user, HttpServletRequest request) {
        SecurityUserDetails securityUser = new SecurityUserDetails(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            securityUser,
            null,
            securityUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        request.getSession().setAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
            SecurityContextHolder.getContext()
        );
    }
}
