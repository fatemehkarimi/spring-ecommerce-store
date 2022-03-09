package com.example.ecommerce.web;

import com.example.ecommerce.service.UserService;
import com.example.ecommerce.web.dto.UserSignupDto;


import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class UserSignupController {
    @Autowired
    private UserService userService;

    public UserSignupController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserSignupDto userSignupDto() {
        return new UserSignupDto();
    }

    @GetMapping
    public String showSignupFrom(Model model) {
        return "signup";
    }

    @PostMapping
    public String signupUserAccount(
        @ModelAttribute("user") UserSignupDto signupDto)
    {
        userService.save(signupDto);
        return "redirect:/signup?success";
    }
}
