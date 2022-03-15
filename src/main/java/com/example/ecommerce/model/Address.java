package com.example.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(columnDefinition="serial")
    private long id;

    @Column(name="city")
    private String city;

    @Column(name="streetAddress")
    private String streetAddress;

    @Column(name="postalCode")
    private String postalCode;

    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName="id", nullable=false)
    private User user;

    Address() {

    }

    public Address(long id, String city,
        String streetAddress, String postalCode, User user)
    {
        this.id = id;
        this.city = city;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.user = user;
    }

    public Address(String city, String streetAddress, String postalCode, User user) {
        this.city = city;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getPostalCod() {
        return postalCode;
    }
}
