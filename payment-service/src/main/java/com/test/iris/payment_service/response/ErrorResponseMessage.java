package com.test.iris.payment_service.response;


public class ErrorResponseMessage {

    private String status;
    private String message;

    public ErrorResponseMessage(String status, String message) {
        this.status = status;
        this.message = message;
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

    @Override
    public String toString() {
        return "ErrorResponseMessage{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
