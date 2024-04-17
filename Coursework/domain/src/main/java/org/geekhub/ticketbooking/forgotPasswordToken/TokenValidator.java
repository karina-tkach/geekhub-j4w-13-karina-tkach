package org.geekhub.ticketbooking.forgotPasswordToken;

import org.geekhub.ticketbooking.exception.TokenValidationException;
import org.geekhub.ticketbooking.user.User;
import org.geekhub.ticketbooking.user.UserService;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Objects;


@Component
public class TokenValidator {
    private final UserService userService;

    public TokenValidator(UserService userService) {
        this.userService = userService;
    }

    public void validate(ForgotPasswordToken token, String operation) {
        if (token == null) {
            throw new TokenValidationException("Token was null");
        }

        validateToken(token.getToken());
        validateUser(token.getUser());
        validateExpireTime(token.getExpireTime());
        validateIsUsed(token.isUsed(), operation);
    }

    private void validateToken(String token) {
        if (token == null) {
            throw new TokenValidationException("Token value was null");
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new TokenValidationException("User was null");
        }
        User existUser = userService.getUserById(user.getId());
        if (existUser == null || !existUser.equals(user)) {
            throw new TokenValidationException("User was not found");
        }
    }

    private void validateExpireTime(OffsetDateTime expireTime) {
        if (expireTime == null || expireTime.isBefore(OffsetDateTime.now())) {
            throw new TokenValidationException("Expire time was invalid");
        }
    }

    private void validateIsUsed(boolean used, String operation) {
        if (Objects.equals(operation, "add") && used) {
            throw new TokenValidationException("Token must not be used");

        } else if (Objects.equals(operation, "update") && !used) {
            throw new TokenValidationException("Token must be used");

        }
    }
}
