package com.example.ecommerce.dao;

import java.util.UUID;

import com.example.ecommerce.model.User;
import com.example.ecommerce.web.dto.UserSignupDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends JpaRepository <User, UUID> {
    User findByEmail(String email);
}
