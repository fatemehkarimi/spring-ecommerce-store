package com.example.ecommerce.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.web.dto.UserSignupDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
@RequestMapping("/user")
public class UserAccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @ModelAttribute("updatedUser")
    public UserSignupDto getUpdatedUserDto() {
        UserSignupDto u = new UserSignupDto();
        User user = getLoggedInUser();
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setPhoneNumber(user.getPhoneNumber());
        u.setEmail(user.getEmail());

        return u;
    }

    @ModelAttribute("user")
    public User getLoggedInUser() {
        Authentication auth = 
            SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails)auth.getPrincipal()).getUsername();
        User user = userDao.findByEmail(username);
        return user;
    }
    
    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showUserProfileEdit(Model model) {
        return "profile_edit";
    }


    @RequestMapping(method=RequestMethod.POST, value="/profile/edit")
    public String updateUserProfile(
        @ModelAttribute("updatedUser") UserSignupDto user, Model model)
    {
        userService.update(user);
        return "profile";
    }
}
