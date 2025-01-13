package com.test.iris.authentication.service.response;



import lombok.Builder;

import java.time.LocalDateTime;


public class Data {

    private Long userId;
    private String username;
    private String email;
    private LocalDateTime createdDate;

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

   /* public static class UserResponseBuilder{
        private Long userId;
        private String username;
        private String email;
        private LocalDateTime createdDate;

        public UserResponseBuilder userId(Long userId){
            this.userId = userId;
            return this;
        }
        public UserResponseBuilder username(String username){
            this.username = username;
            return this;
        }
        public UserResponseBuilder email(String email){
            this.email = email;
            return this;
        }
        public UserResponseBuilder createdDate(LocalDateTime createdDate){
            this.createdDate = createdDate;
            return this;
        }

    }*/
}
