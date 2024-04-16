package org.geekhub.ticketbooking.model;

import java.time.OffsetDateTime;

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
}
