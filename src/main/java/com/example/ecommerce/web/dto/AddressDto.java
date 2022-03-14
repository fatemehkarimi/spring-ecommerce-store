package com.example.ecommerce.web.dto;

import com.example.ecommerce.model.User;

public class AddressDto {
    private long id = -1;
    private String city;
    private String streetAddress;
    private String postalCode;
    private User user;

    public AddressDto() {

    }

    public AddressDto(
        long id, String city, String streetAddress, String postalCode, User user)
    {
        this.id = id;
        this.city = city;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.user = user;
    }

    public AddressDto(String city, String streetAddress, String postalCode, User user) {
        this.city = city;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
