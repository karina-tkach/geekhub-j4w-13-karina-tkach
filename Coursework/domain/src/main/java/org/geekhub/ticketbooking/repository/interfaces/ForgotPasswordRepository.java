package org.geekhub.ticketbooking.repository.interfaces;

import org.geekhub.ticketbooking.model.ForgotPasswordToken;

public interface ForgotPasswordRepository {
    int addToken(ForgotPasswordToken token, int userId);

    void updateTokenById(ForgotPasswordToken token, int id, int userId);

    ForgotPasswordToken getTokenByValue(String token);

    ForgotPasswordToken getTokenById(int id);
}
