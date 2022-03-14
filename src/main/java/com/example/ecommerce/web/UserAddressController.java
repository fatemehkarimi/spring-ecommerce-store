package com.example.ecommerce.web;

import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.AddressService;
import com.example.ecommerce.web.dto.AddressDto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/address")
public class UserAddressController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressService addressService;
    
    public User getLoggedInUser() {
        Authentication auth = 
            SecurityContextHolder.getContext().getAuthentication();
    
        String username = ((UserDetails)auth.getPrincipal()).getUsername();
        User user = userDao.findByEmail(username);
        return user;
    }


    @GetMapping
    public String showUserAddresses() {
        return "address";
    }

    @GetMapping("/add")
    public String showAddNewAddress(Model model) {
        AddressDto addressDto = new AddressDto();
        model.addAttribute("address", addressDto);
        return "address_add";
    }

    @PostMapping("/add")
    public String addNewAddress(
        @ModelAttribute("address") AddressDto addressDto) 
    {
        addressDto.setUser(getLoggedInUser());
        addressService.save(addressDto);
        return "address";
    }
}
