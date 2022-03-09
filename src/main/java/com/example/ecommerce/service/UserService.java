package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.web.dto.UserSignupDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(UserSignupDto signupDto);
}
