package com.example.ecommerce.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.validation.Valid;

import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.model.User;
import com.example.ecommerce.model.Address;
import com.example.ecommerce.service.AddressService;
import com.example.ecommerce.web.dto.AddressDto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/address")
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
    public ResponseEntity <Set <Address> > getUserAddresses() {
        User user = getLoggedInUser();
        return new ResponseEntity <Set <Address> >
            (user.getAddresses(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public void addNewAddress(
        @Valid @NonNull @RequestBody AddressDto addressDto) 
    {
        addressDto.setUser(getLoggedInUser());
        addressService.save(addressDto);
    }
}
