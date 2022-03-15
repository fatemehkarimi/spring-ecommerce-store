package com.example.ecommerce.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.web.dto.UserSignupDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/user")
public class UserAccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    public User getLoggedInUser() {
        Authentication auth = 
            SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails)auth.getPrincipal()).getUsername();
        User user = userDao.findByEmail(username);
        return user;
    }
    
    @GetMapping("/profile")
    public ResponseEntity<User> showUserProfile() {
        User user = getLoggedInUser();
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/profile/edit")
    public void updateUserProfile(
        @RequestBody UserSignupDto user)
    {
        User loggedInUser = getLoggedInUser();
        user.setEmail(loggedInUser.getEmail());
        userService.update(user);
    }
}
