package org.geekhub.ticketbooking.service;

import java.io.UnsupportedEncodingException;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.mail.MessagingException;
import org.geekhub.ticketbooking.exception.TokenValidationException;
import org.geekhub.ticketbooking.model.ForgotPasswordToken;
import org.geekhub.ticketbooking.repository.interfaces.ForgotPasswordRepository;
import org.geekhub.ticketbooking.validator.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {

    private final ForgotPasswordRepository forgotPasswordRepository;
    private final MailSenderService mailSenderService;
    private final Logger logger = LoggerFactory.getLogger(ForgotPasswordService.class);
    private final TokenValidator tokenValidator;

    public ForgotPasswordService(ForgotPasswordRepository forgotPasswordRepository,
                                 MailSenderService mailSenderService, TokenValidator tokenValidator) {
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.mailSenderService = mailSenderService;
        this.tokenValidator = tokenValidator;
    }

    private static final int MINUTES_ACTIVE_TIME = 10;

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public OffsetDateTime expireTimeRange() {
        return OffsetDateTime.now().plusMinutes(MINUTES_ACTIVE_TIME);
    }

    public void sendEmail(String to, String subject, String emailLink) throws MailException, MessagingException, UnsupportedEncodingException {

        String emailContent = "<p>Hello!</p>"
            + "Click the link the below to reset password"
            + "<p><a href=\"" + emailLink + "\">Change My Password</a></p>"
            + "<br>"
            + "Ignore this email if you did not made the request";

        mailSenderService.sendEmail(to, subject, emailContent);
    }

    public boolean isExpired(ForgotPasswordToken forgotPasswordToken) {
        return OffsetDateTime.now().isAfter(forgotPasswordToken.getExpireTime());
    }

    public String checkValidity(ForgotPasswordToken forgotPasswordToken) {

        if (forgotPasswordToken == null) {
            return "Invalid Token";
        } else if (forgotPasswordToken.isUsed()) {
            return "The token is already used";
        } else if (isExpired(forgotPasswordToken)) {
            return "The token is expired";
        } else {
            return null;
        }
    }

    public ForgotPasswordToken getTokenByValue(String token) {
        try {
            logger.info("Try to get token by value");
            ForgotPasswordToken passwordToken = forgotPasswordRepository.getTokenByValue(token);
            logger.info("Token was fetched successfully");
            return passwordToken;
        } catch (DataAccessException exception) {
            logger.warn("Token wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }

    public ForgotPasswordToken addToken(ForgotPasswordToken token) {
        try {
            logger.info("Try to add token");
            tokenValidator.validate(token, "add");

            int id = forgotPasswordRepository.addToken(token, token.getUser().getId());
            if (id == -1) {
                throw new TokenValidationException("Unable to retrieve the generated key");
            }

            logger.info("Token was added:\n{}", token);
            return getTokenByValue(token.getToken());
        } catch (TokenValidationException | DataAccessException exception) {
            logger.warn("Token wasn't added: {}\n{}", token, exception.getMessage());
            return null;
        }
    }

    public ForgotPasswordToken updateTokenById(ForgotPasswordToken token, int id) {
        ForgotPasswordToken tokenToUpdate = getTokenById(id);
        try {
            logger.info("Try to update token");
            if (tokenToUpdate == null) {
                throw new TokenValidationException("Token with id '" + id + "' not found");
            }
            tokenValidator.validate(token, "update");
            boolean sameToken = token.getToken().equals(tokenToUpdate.getToken()) &&
                token.getExpireTime().equals(tokenToUpdate.getExpireTime()) &&
                token.getUser().equals(tokenToUpdate.getUser());
            if (!sameToken) {
                throw new TokenValidationException("Token update must be only by isUsed value");
            }

            forgotPasswordRepository.updateTokenById(token, id, token.getUser().getId());
            logger.info("Token was updated:\n{}", token);
            return getTokenById(id);
        } catch (TokenValidationException | DataAccessException exception) {
            logger.warn("Token wasn't updated: {}\n{}", token, exception.getMessage());
            return null;
        }
    }

    public ForgotPasswordToken getTokenById(int id) {
        try {
            logger.info("Try to get token by id");
            ForgotPasswordToken token = forgotPasswordRepository.getTokenById(id);
            logger.info("Token was fetched successfully");
            return token;
        } catch (DataAccessException exception) {
            logger.warn("Token wasn't fetched\n{}", exception.getMessage());
            return null;
        }
    }
}
