package org.geekhub.ticketbooking.forgotPasswordToken;

public interface ForgotPasswordTokenRepository {
    int addToken(ForgotPasswordToken token, int userId);

    void updateTokenById(ForgotPasswordToken token, int id, int userId);

    ForgotPasswordToken getTokenByValue(String token);

    ForgotPasswordToken getTokenById(int id);
}
