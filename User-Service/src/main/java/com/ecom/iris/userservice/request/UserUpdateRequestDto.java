package com.ecom.iris.userservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class UserUpdateRequestDto {
    // @Schema(description = "Unique username of the user", example = "john_doe")

    @Size(min = 0, max = 0,message = "username should not allowed to update")
    private String username;

    @Size(min = 0, max = 0,message = "email should not allowed to update")
    private String email;


    @Column(name = "password_hash")
    private String password;


    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")

    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;
    //  @Schema(description = "address of the user", example = "Agresen nagar E-2")
    private String address;
    // @Schema(description = "city name of the user", example = "Ajmer")
    private String city;
    //@Schema(description = "state name of the user", example = "Delhi")
    private String state;
    //   @Schema(description = "country name of the user", example = "India")
    private String country;
    //  @Schema(description = "postal code of the user", example = "305801")
    @Column(name = "postal_code")
    private String postalCode;
    // @Schema(description = "user is active or inactive", example = "true/false")
    private boolean isActive;
    //  @Schema(description = "user registration date time", example = "Timestamp format")
    @Column(updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
    //@Schema(description = "user update  date time", example = "Timestamp format")
    private LocalDateTime updatedDate = LocalDateTime.now();

    public UserUpdateRequestDto(String username, String email, String password, String firstName, String lastName, String phoneNumber, String address, String city, String state, String country, String postalCode, boolean isActive, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.isActive = isActive;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "UserUpdateRequestDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", isActive=" + isActive +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
