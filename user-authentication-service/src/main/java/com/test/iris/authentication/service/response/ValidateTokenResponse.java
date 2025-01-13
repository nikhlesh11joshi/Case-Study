package com.test.iris.authentication.service.response;

import java.time.LocalDateTime;

public class ValidateTokenResponse {
    private String status;
    private String message;
    private LocalDateTime tokenExpiredAt;

    public ValidateTokenResponse(String status, String message, LocalDateTime tokenExpiredAt) {
        this.status = status;
        this.message = message;
        this.tokenExpiredAt = tokenExpiredAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTokenExpiredAt() {
        return tokenExpiredAt;
    }

    public void setTokenExpiredAt(LocalDateTime tokenExpiredAt) {
        this.tokenExpiredAt = tokenExpiredAt;
    }

    @Override
    public String toString() {
        return "ValidateTokenResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", tokenExpiredAt=" + tokenExpiredAt +
                '}';
    }
}
