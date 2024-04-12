package org.geekhub.ticketbooking.service;

import java.io.UnsupportedEncodingException;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.geekhub.ticketbooking.model.ForgotPasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class ForgotPasswordService {

    @Autowired
    JavaMailSender javaMailSender;

    private final int MINUTES = 10;

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public OffsetDateTime expireTimeRange() {
        return OffsetDateTime.now().plusMinutes(MINUTES);
    }

    public void sendEmail(String to, String subject, String emailLink) throws MailException, MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String emailContent = "<p>Hello</p>"
            + "Click the link the below to reset password"
            + "<p><a href=\""+ emailLink + "\">Change My Password</a></p>"
            + "<br>"
            + "Ignore this Email if you did not made the request";
        helper.setText(emailContent, true);
        helper.setFrom("cinema@gmail.com", "Cinema Support");
        helper.setSubject(subject);
        helper.setTo(to);
        javaMailSender.send(message);
    }

    public boolean isExpired (ForgotPasswordToken forgotPasswordToken) {
        return OffsetDateTime.now().isAfter(forgotPasswordToken.getExpireTime());
    }

    public String checkValidity (ForgotPasswordToken forgotPasswordToken, Model model) {

        if (forgotPasswordToken == null) {
            model.addAttribute("error", "Invalid Token");
            return "error-page";
        }

        else if (forgotPasswordToken.isUsed()) {
            model.addAttribute("error", "The token is already used");
            return "error-page";
        }

        else if (isExpired(forgotPasswordToken)) {
            model.addAttribute("error", "The token is expired");
            return "error-page";
        }
        else {
            return "reset-password";
        }
    }
}
