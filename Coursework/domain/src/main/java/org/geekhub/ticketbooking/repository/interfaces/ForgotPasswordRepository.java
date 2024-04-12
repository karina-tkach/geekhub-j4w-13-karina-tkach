package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.ForgotPasswordToken;
import org.geekhub.ticketbooking.model.User;

import java.util.List;

public interface ForgotPasswordRepository {
    List<ForgotPasswordToken> getTokens();

    int addToken(ForgotPasswordToken token, int userId);

    ForgotPasswordToken getTokenById(int id);

    List<ForgotPasswordToken> getTokenByEmail(String email);

    List<ForgotPasswordToken> getTokenByUser(int userId);

    void deleteTokenById(int id);

    void updateTokenById(ForgotPasswordToken token, int id, int userId);

    ForgotPasswordToken findByToken(String token);
}
