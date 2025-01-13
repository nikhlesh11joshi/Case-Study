package com.test.iris.authentication.service.response;

import java.time.LocalDateTime;

public class TokenResponse {
    private String token;
    private LocalDateTime expiryDate;

    public TokenResponse(String token, LocalDateTime expiryDate) {

        this.token = token;
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "token='" + token + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
