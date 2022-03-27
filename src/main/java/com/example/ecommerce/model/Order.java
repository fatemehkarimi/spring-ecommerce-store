package com.example.ecommerce.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="user_order")
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(columnDefinition="serial")
    private long id;

    @ManyToOne
    @JoinColumn(name="userId", referencedColumnName="id", nullable=false)
    private User user;

    @ManyToMany
    @JoinTable(
        name="order_products",
        joinColumns=@JoinColumn(name="order_id"),
        inverseJoinColumns=@JoinColumn(name="product_id")
    )
    Set <Product> products;

    public Order() {}

    public Order(long id, User user) {
        this.id = id;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public Set<Product> getProducts() {
        return products;
    }
}
