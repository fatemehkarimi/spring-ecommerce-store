package com.example.ecommerce.web.dto;

import com.example.ecommerce.model.User;

public class AddressDto {
    private String city;
    private String streetAddress;
    private String postalCode;
    private User user;

    public AddressDto() {

    }

    public AddressDto(String city, String streetAddress, String postalCode, User user) {
        this.city = city;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
