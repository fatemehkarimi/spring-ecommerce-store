package com.example.ecommerce.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(columnDefinition="serial")
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private double price;

    @Column(name="category")
    private String category;

    @ManyToMany(mappedBy="products")
    Set <Order> orders;

    Product() {}

    public Product(long id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
