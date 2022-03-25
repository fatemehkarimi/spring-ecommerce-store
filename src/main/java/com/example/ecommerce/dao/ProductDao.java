package com.example.ecommerce.dao;

import com.example.ecommerce.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductDao extends JpaRepository <Product, Long> {
}
