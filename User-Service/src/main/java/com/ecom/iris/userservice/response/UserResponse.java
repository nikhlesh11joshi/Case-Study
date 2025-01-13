package com.ecom.iris.userservice.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@Builder
@Schema(description = "User response model")
public class UserResponse {
    @Schema(description = "status of user", example = "success or error")
    private String status;
    @Schema(description = "status of user", example = "success or error")
    private String message;
    @Schema(description = "status of user", example = "success or error")
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
