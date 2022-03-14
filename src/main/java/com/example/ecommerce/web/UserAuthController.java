package com.example.ecommerce.web;

import javax.validation.Valid;

import com.example.ecommerce.service.UserService;
import com.example.ecommerce.web.dto.UserSignupDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public void signupUserAccount(
        @Valid @NonNull @RequestBody UserSignupDto signupDto)
    {
        userService.save(signupDto);
    }
}
