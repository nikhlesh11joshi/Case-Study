package com.test.iris.authentication.service.response;



/*

public class UserResponse {
    private String status;
    private String message;
    private Data data;

    private UserResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.data = builder.data;
    }

    public static class Builder {
        private String status;
        private String message;
        private Data data;

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder data(Data data) {
            this.data = data;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(this);
        }
    }
    @Override
    public String toString() {
        return "userResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
}
*/



public class UserResponse {

    private String status;

    private String message;

    private Data data;


   public UserResponse() {
    }

    public UserResponse(String status, String message, Data data) {
        this.status = status;
        this.message = message;
        this.data = data;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "userResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

