package com.example.ecommerce.web;

import javax.validation.Valid;

import com.example.ecommerce.service.UserService;
import com.example.ecommerce.web.dto.UserLoginDto;
import com.example.ecommerce.web.dto.UserSignupDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(
        @Valid @NonNull @RequestBody UserLoginDto loginDto)
    {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        return new ResponseEntity<>("user is now loggged in", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public void signupUserAccount(
        @Valid @NonNull @RequestBody UserSignupDto signupDto)
    {
        userService.save(signupDto);
    }
}
