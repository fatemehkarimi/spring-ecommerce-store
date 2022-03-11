package com.example.ecommerce.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("/user")
public class UserAccountController {
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
    public String showUserProfile(Model model) {
        User user = getLoggedInUser();
            model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showUserProfileEdit(Model model) {
        User user = getLoggedInUser();
        model.addAttribute("user", user);
        return "profile_edit";
    }
}
