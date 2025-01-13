package com.ecom.iris.userservice.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder

@Schema(description = "User response model")
public class Data {

    @Schema(description = "Unique identifier of the user", example = "1")
    private Long userId;
    @Schema(description = "Unique username of the user", example = "john_doe")
    private String username;
    @Schema(description = "Unique email of the user", example = "testuser@123gmail.com")
    private String email;
    @Schema(description = "user registration date time", example = "Timestamp format")
    private LocalDateTime createdDate = LocalDateTime.now();

    public Data() {
    }

    public Data(Long userId, String username, String email, LocalDateTime createdDate) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.createdDate = createdDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Data{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
