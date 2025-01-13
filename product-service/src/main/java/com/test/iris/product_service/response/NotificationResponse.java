package com.test.iris.product_service.response;

public class NotificationResponse {

    private String message;
    private String status;

    public NotificationResponse() {

    }

    public NotificationResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NotificationResponse{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
