package org.geekhub.ticketbooking.forgotPasswordToken;

import org.geekhub.ticketbooking.user.User;

import java.time.OffsetDateTime;
import java.util.Objects;

public class ForgotPasswordToken {
    private int id;
    private String token;
    private User user;
    private OffsetDateTime expireTime;
    private boolean isUsed;

    public ForgotPasswordToken() {
        this.id = -1;
        this.token = null;
        this.user = null;
        this.expireTime = null;
        this.isUsed = false;
    }

    public ForgotPasswordToken(int id, String token, User user, OffsetDateTime expireTime, boolean isUsed) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expireTime = expireTime;
        this.isUsed = isUsed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OffsetDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(OffsetDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForgotPasswordToken that = (ForgotPasswordToken) o;
        return id == that.id && isUsed == that.isUsed && Objects.equals(token, that.token) &&
            Objects.equals(user, that.user) && Objects.equals(expireTime, that.expireTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, user, expireTime, isUsed);
    }

    @Override
    public String toString() {
        return "ForgotPasswordToken{" +
            "id=" + id +
            ", token='" + token + '\'' +
            ", user=" + user +
            ", expireTime=" + expireTime +
            ", isUsed=" + isUsed +
            '}';
    }
}
