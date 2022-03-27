package com.example.ecommerce.dao;

import com.example.ecommerce.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDao extends JpaRepository <Order, Long> {
}
