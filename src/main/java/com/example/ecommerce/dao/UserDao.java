package com.example.ecommerce.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import com.example.ecommerce.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends JpaRepository <User, UUID> {
    User findByEmail(String email);

    @Modifying
    @Query(value="delete from user_info where id = :user_id", nativeQuery=true)
    void deleteById(@Param("user_id") UUID id);
}
