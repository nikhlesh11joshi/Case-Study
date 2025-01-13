package com.test.iris.order_service.model;

import com.test.iris.order_service.response.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;
    @NotNull
    @Column(name = "user_id", nullable = false)
    private  Long userId;
    @Column(nullable = false, length = 50)
    private String status;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt= LocalDateTime.now();
    @NotNull
    @Size(max = 150)
    private String note;

    @NotNull
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @NotNull
    @Column(name = "tracking_id", nullable = false)
    private String trackingId;
    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;
    @NotNull
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;
    @NotNull
    @Column(name = "is_email_notification_send", nullable = false)
    private boolean isEmailNotificationSend;
    @NotNull
    @Column(name = "is_phone_notification_send", nullable = false)
    private boolean isPhoneNotificationSend;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderProduct> orderProductList;

    public Order() {
    }

    public Order(Long orderId, Long userId, String status, LocalDateTime createdAt, LocalDateTime updatedAt, String note, Double totalAmount, String trackingId, String paymentMethod, String paymentStatus, boolean isEmailNotificationSend, boolean isPhoneNotificationSend, List<OrderProduct> orderProductList) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.note = note;
        this.totalAmount = totalAmount;
        this.trackingId = trackingId;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.isEmailNotificationSend = isEmailNotificationSend;
        this.isPhoneNotificationSend = isPhoneNotificationSend;
        this.orderProductList = orderProductList;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isEmailNotificationSend() {
        return isEmailNotificationSend;
    }

    public void setEmailNotificationSend(boolean emailNotificationSend) {
        isEmailNotificationSend = emailNotificationSend;
    }

    public boolean isPhoneNotificationSend() {
        return isPhoneNotificationSend;
    }

    public void setPhoneNotificationSend(boolean phoneNotificationSend) {
        isPhoneNotificationSend = phoneNotificationSend;
    }

    public List<OrderProduct> getOrderProductList() {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProduct> orderProductList) {
        this.orderProductList = orderProductList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", note='" + note + '\'' +
                ", totalAmount=" + totalAmount +
                ", trackingId='" + trackingId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", isEmailNotificationSend=" + isEmailNotificationSend +
                ", isPhoneNotificationSend=" + isPhoneNotificationSend /*+
                ", orderProductList=" + orderProductList*/ +
                '}';
    }
}
